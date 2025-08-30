package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.VideoCall;
import com.dreamers.the_dreamers.repository.VideoCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoCallService {
    
    private final VideoCallRepository videoCallRepository;
    
    public List<VideoCall> getAllVideoCalls() {
        return videoCallRepository.findAll();
    }
    
    public Optional<VideoCall> getVideoCallById(String id) {
        return videoCallRepository.findById(id);
    }
    
    public List<VideoCall> getVideoCallsByOrganizerId(String organizerId) {
        return videoCallRepository.findByOrganizerId(organizerId);
    }
    
    public VideoCall createVideoCall(VideoCall videoCall) {
        return videoCallRepository.save(videoCall);
    }
    
    public VideoCall updateVideoCall(String id, VideoCall videoCallDetails) {
        VideoCall videoCall = videoCallRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("VideoCall not found"));
        
        videoCall.setTitle(videoCallDetails.getTitle());
        videoCall.setStartTime(videoCallDetails.getStartTime());
        videoCall.setEndTime(videoCallDetails.getEndTime());
        videoCall.setCallLink(videoCallDetails.getCallLink());
        videoCall.setStatus(videoCallDetails.getStatus());
        
        return videoCallRepository.save(videoCall);
    }
    
    public void deleteVideoCall(String id) {
        videoCallRepository.deleteById(id);
    }
}
