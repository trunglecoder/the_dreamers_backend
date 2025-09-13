package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.dto.auth.*;
import com.dreamers.the_dreamers.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Authentication API", description = "API for user authentication, registration, and password management")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "User login",
            description = "Authenticates a user and returns a JWT and refresh token.")
    @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", description = "Invalid credentials")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Login credentials", required = true)
            @Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User registration",
            description = "Registers a new user and sends a verification email. Returns a JWT upon registration.")
    @ApiResponse(responseCode = "200", description = "Registration successful",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request or user already exists")
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User registration details", required = true)
            @Valid @RequestBody RegisterRequest registerRequest) {
        LoginResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "User logout",
            description = "Clears the security context for the current user.")
    @ApiResponse(responseCode = "200", description = "Logout successful")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Refresh access token",
            description = "Generates a new JWT using a valid refresh token.")
    @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized or invalid refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(
            @Parameter(description = "Refresh token received during login", required = true)
            @RequestParam String refreshToken) {
        LoginResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Verify user account",
            description = "Verifies a user's account using the token sent to their email.")
    @ApiResponse(responseCode = "200", description = "Account verified successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or expired verification token")
    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(
            @Parameter(description = "Verification token from the email link", required = true)
            @RequestParam("token") String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok("Account verified successfully!");
    }

    @Operation(summary = "Get current user info",
            description = "Returns information about the currently authenticated user.")
    @ApiResponse(responseCode = "200", description = "User info retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @GetMapping("/me")
    public ResponseEntity<LoginResponse.UserInfo> getCurrentUser() {
        // This would typically get the current user from the security context
        // For now, we'll return a simple response
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Forgot password",
            description = "Sends a password reset email to the user.")
    @ApiResponse(responseCode = "200", description = "Password reset email sent successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User's email address", required = true)
            @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok("Please check your email to reset your password.");
    }

    @Operation(summary = "Reset password",
            description = "Resets the user's password using a valid reset token.")
    @ApiResponse(responseCode = "200", description = "Password reset successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or expired reset token")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Reset token and new password", required = true)
            @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok("Your password has been successfully changed!");
    }
}