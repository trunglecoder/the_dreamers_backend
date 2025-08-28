package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.VideoCall;
import com.dreamers.the_dreamers.service.VideoCallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video-calls")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VideoCallController {
    
    private final VideoCallService videoCallService;
    
    @GetMapping
    public ResponseEntity<List<VideoCall>> getAllVideoCalls() {
        return ResponseEntity.ok(videoCallService.getAllVideoCalls());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VideoCall> getVideoCallById(@PathVariable String id) {
        return videoCallService.getVideoCallById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<VideoCall>> getVideoCallsByOrganizer(@PathVariable String organizerId) {
        return ResponseEntity.ok(videoCallService.getVideoCallsByOrganizerId(organizerId));
    }
    
    @PostMapping
    public ResponseEntity<VideoCall> createVideoCall(@RequestBody VideoCall videoCall) {
        return ResponseEntity.ok(videoCallService.createVideoCall(videoCall));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VideoCall> updateVideoCall(@PathVariable String id, @RequestBody VideoCall videoCallDetails) {
        try {
            VideoCall updatedVideoCall = videoCallService.updateVideoCall(id, videoCallDetails);
            return ResponseEntity.ok(updatedVideoCall);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoCall(@PathVariable String id) {
        videoCallService.deleteVideoCall(id);
        return ResponseEntity.noContent().build();
    }
}
