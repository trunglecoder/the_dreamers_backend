package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.config.JwtUtil;
import com.dreamers.the_dreamers.dto.LoginRequest;
import com.dreamers.the_dreamers.dto.LoginResponse;
import com.dreamers.the_dreamers.dto.RegisterRequest;
import com.dreamers.the_dreamers.exception.AppException;
import com.dreamers.the_dreamers.exception.ErrorCode;
import com.dreamers.the_dreamers.model.Role;
import com.dreamers.the_dreamers.model.User;
import com.dreamers.the_dreamers.model.UserDetails;
import com.dreamers.the_dreamers.repository.RoleRepository;
import com.dreamers.the_dreamers.repository.UserDetailsRepository;
import com.dreamers.the_dreamers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            if (!user.getVerified()) {
                throw new AppException(ErrorCode.ACCOUNT_NOT_VERIFIED);
            }

            String token = jwtUtil.generateToken(user.getEmail(), getUserRoleName(user));
            String refreshToken = generateRefreshToken();

            return LoginResponse.builder()
                    .token(token)
                    .refreshToken(refreshToken)
                    .expiresIn(3600L)
                    .userInfo(buildUserInfo(user))
                    .build();

        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
    }

    @Transactional
    public LoginResponse register(RegisterRequest registerRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // Find the role from the database
        Role role = roleRepository.findByName(registerRequest.getRoleName())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        // Create and save the new User
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role) // Gán trực tiếp đối tượng Role
                .verified(false) // Mặc định chưa verified sau khi đăng ký
                .verificationToken(generateVerificationToken())
                .build();

        userRepository.save(user);

        // Create and save UserDetails
        UserDetails userDetail = UserDetails.builder()
                .user(user)
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .isVerified(false) // Mặc định chưa verified
                .donationCount(0)
                .build();

        userDetailRepository.save(userDetail); // Lưu UserDetail

        // Generate token for immediate login (có thể cần thay đổi logic này)
        String token = jwtUtil.generateToken(user.getEmail(), role.getName());
        String refreshToken = generateRefreshToken();

        return LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .expiresIn(3600L)
                .userInfo(buildUserInfo(user))
                .build();
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    public LoginResponse refreshToken(String refreshToken) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            String newToken = jwtUtil.generateToken(user.getEmail(), getUserRoleName(user));
            String newRefreshToken = generateRefreshToken();

            return LoginResponse.builder()
                    .token(newToken)
                    .refreshToken(newRefreshToken)
                    .expiresIn(3600L)
                    .userInfo(buildUserInfo(user))
                    .build();
        }

        throw new AppException(ErrorCode.UNAUTHORIZED);
    }

    private String getUserRoleName(User user) {
        if (user.getRole() != null) {
            return user.getRole().getName();
        }
        return "USER"; // Trả về role mặc định nếu không tìm thấy
    }

    private LoginResponse.UserInfo buildUserInfo(User user) {
        UserDetails userDetail = user.getUserDetails();
        return LoginResponse.UserInfo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(userDetail != null ? userDetail.getFirstName() : null)
                .lastName(userDetail != null ? userDetail.getLastName() : null)
                .verified(user.getVerified())
                .roles(getUserRoleName(user))
                .build();
    }

    private String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }

    private String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }
}