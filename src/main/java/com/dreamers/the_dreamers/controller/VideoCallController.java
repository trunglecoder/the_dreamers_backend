package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.VideoCall;
import com.dreamers.the_dreamers.service.VideoCallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/video-calls")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Video Call API", description = "API for managing video call sessions")
public class VideoCallController {

    private final VideoCallService videoCallService;

    @Operation(summary = "Get all video calls",
            description = "Returns a list of all video call sessions.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VideoCall.class)))
    @GetMapping
    public ResponseEntity<List<VideoCall>> getAllVideoCalls() {
        return ResponseEntity.ok(videoCallService.getAllVideoCalls());
    }

    @Operation(summary = "Get video call by ID",
            description = "Returns a single video call session based on its ID.")
    @ApiResponse(responseCode = "200", description = "Video call found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VideoCall.class)))
    @ApiResponse(responseCode = "404", description = "Video call not found")
    @GetMapping("/{id}")
    public ResponseEntity<VideoCall> getVideoCallById(
            @Parameter(description = "ID of the video call to retrieve")
            @PathVariable String id) {
        return videoCallService.getVideoCallById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get video calls by organizer ID",
            description = "Returns a list of all video call sessions organized by a specific user.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VideoCall.class)))
    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<VideoCall>> getVideoCallsByOrganizer(
            @Parameter(description = "ID of the organizer user")
            @PathVariable String organizerId) {
        return ResponseEntity.ok(videoCallService.getVideoCallsByOrganizerId(organizerId));
    }

    @Operation(summary = "Create a new video call",
            description = "Creates a new video call session.")
    @ApiResponse(responseCode = "200", description = "Video call created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VideoCall.class)))
    @PostMapping
    public ResponseEntity<VideoCall> createVideoCall(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new video call", required = true)
            @RequestBody VideoCall videoCall) {
        return ResponseEntity.ok(videoCallService.createVideoCall(videoCall));
    }

    @Operation(summary = "Update a video call",
            description = "Updates an existing video call session based on its ID.")
    @ApiResponse(responseCode = "200", description = "Video call updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VideoCall.class)))
    @ApiResponse(responseCode = "404", description = "Video call not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<VideoCall> updateVideoCall(
            @Parameter(description = "ID of the video call to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated video call details", required = true)
            @RequestBody VideoCall videoCallDetails) {
        try {
            VideoCall updatedVideoCall = videoCallService.updateVideoCall(id, videoCallDetails);
            return ResponseEntity.ok(updatedVideoCall);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a video call",
            description = "Deletes a video call session based on its ID.")
    @ApiResponse(responseCode = "204", description = "Video call deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoCall(
            @Parameter(description = "ID of the video call to delete")
            @PathVariable String id) {
        videoCallService.deleteVideoCall(id);
        return ResponseEntity.noContent().build();
    }
}