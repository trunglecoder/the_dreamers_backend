package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.UserDetails;
import com.dreamers.the_dreamers.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    
    private final UserDetailsRepository userDetailsRepository;
    
    public List<UserDetails> getAllUserDetails() {
        return userDetailsRepository.findAll();
    }
    
    public Optional<UserDetails> getUserDetailsById(String id) {
        return userDetailsRepository.findById(id);
    }
    
    public Optional<UserDetails> getUserDetailsByUserId(Long userId) {
        return Optional.ofNullable(userDetailsRepository.findByUserId(userId));
    }
    
    public UserDetails createUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
    
    public UserDetails updateUserDetails(String id, UserDetails userDetailsDetails) {
        UserDetails userDetails = userDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserDetails not found"));
        
        userDetails.setFirstName(userDetailsDetails.getFirstName());
        userDetails.setLastName(userDetailsDetails.getLastName());
        userDetails.setDob(userDetailsDetails.getDob());
        userDetails.setPhoneNumber(userDetailsDetails.getPhoneNumber());
        userDetails.setAddress(userDetailsDetails.getAddress());
        userDetails.setIsVerified(userDetailsDetails.getIsVerified());
        userDetails.setBio(userDetailsDetails.getBio());
        userDetails.setOccupation(userDetailsDetails.getOccupation());
        userDetails.setDonationCount(userDetailsDetails.getDonationCount());
        
        return userDetailsRepository.save(userDetails);
    }
    
    public void deleteUserDetails(String id) {
        userDetailsRepository.deleteById(id);
    }
}
