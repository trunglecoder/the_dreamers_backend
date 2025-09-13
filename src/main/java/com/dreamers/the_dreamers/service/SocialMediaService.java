package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.SocialMedia;
import com.dreamers.the_dreamers.repository.SocialMediaRepository;
import com.dreamers.the_dreamers.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialMediaService {
    
    private final SocialMediaRepository socialMediaRepository;
    private final UserDetailsRepository userDetailsRepository;
    
    public List<SocialMedia> getAllSocialMedia() {
        return socialMediaRepository.findAll();
    }
    
    public Optional<SocialMedia> getSocialMediaById(String id) {
        return socialMediaRepository.findById(id);
    }
    
    public List<SocialMedia> getSocialMediaByUserDetailId(Long userDetailId) {
        return socialMediaRepository.findByUserDetails(userDetailsRepository.findByUserId(userDetailId));
    }
    
    public SocialMedia createSocialMedia(SocialMedia socialMedia) {
        return socialMediaRepository.save(socialMedia);
    }
    
    public SocialMedia updateSocialMedia(String id, SocialMedia socialMediaDetails) {
        SocialMedia socialMedia = socialMediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SocialMedia not found"));
        
        socialMedia.setPlatformName(socialMediaDetails.getPlatformName());
        socialMedia.setProfileUrl(socialMediaDetails.getProfileUrl());
        
        return socialMediaRepository.save(socialMedia);
    }
    
    public void deleteSocialMedia(String id) {
        socialMediaRepository.deleteById(id);
    }
}
