package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.SocialMedia;
import com.dreamers.the_dreamers.service.SocialMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social-media")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SocialMediaController {
    
    private final SocialMediaService socialMediaService;
    
    @GetMapping
    public ResponseEntity<List<SocialMedia>> getAllSocialMedia() {
        return ResponseEntity.ok(socialMediaService.getAllSocialMedia());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SocialMedia> getSocialMediaById(@PathVariable String id) {
        return socialMediaService.getSocialMediaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user-detail/{userDetailId}")
    public ResponseEntity<List<SocialMedia>> getSocialMediaByUserDetail(@PathVariable String userDetailId) {
        return ResponseEntity.ok(socialMediaService.getSocialMediaByUserDetailId(userDetailId));
    }
    
    @PostMapping
    public ResponseEntity<SocialMedia> createSocialMedia(@RequestBody SocialMedia socialMedia) {
        return ResponseEntity.ok(socialMediaService.createSocialMedia(socialMedia));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SocialMedia> updateSocialMedia(@PathVariable String id, @RequestBody SocialMedia socialMediaDetails) {
        try {
            SocialMedia updatedSocialMedia = socialMediaService.updateSocialMedia(id, socialMediaDetails);
            return ResponseEntity.ok(updatedSocialMedia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialMedia(@PathVariable String id) {
        socialMediaService.deleteSocialMedia(id);
        return ResponseEntity.noContent().build();
    }
}
