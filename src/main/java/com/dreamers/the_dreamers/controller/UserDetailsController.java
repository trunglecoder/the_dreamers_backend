package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.UserDetails;
import com.dreamers.the_dreamers.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-details")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserDetailsController {
    
    private final UserDetailsService userDetailsService;
    
    @GetMapping
    public ResponseEntity<List<UserDetails>> getAllUserDetails() {
        return ResponseEntity.ok(userDetailsService.getAllUserDetails());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserDetailsById(@PathVariable String id) {
        return userDetailsService.getUserDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDetails> getUserDetailsByUserId(@PathVariable String userId) {
        return userDetailsService.getUserDetailsByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<UserDetails> createUserDetails(@RequestBody UserDetails userDetails) {
        return ResponseEntity.ok(userDetailsService.createUserDetails(userDetails));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDetails> updateUserDetails(@PathVariable String id, @RequestBody UserDetails userDetailsDetails) {
        try {
            UserDetails updatedUserDetails = userDetailsService.updateUserDetails(id, userDetailsDetails);
            return ResponseEntity.ok(updatedUserDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetails(@PathVariable String id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.noContent().build();
    }
}
