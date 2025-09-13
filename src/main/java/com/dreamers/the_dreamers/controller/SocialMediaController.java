package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.SocialMedia;
import com.dreamers.the_dreamers.service.SocialMediaService;
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
@RequestMapping("/api/social-media")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Social Media API", description = "API for managing user social media links")
public class SocialMediaController {

    private final SocialMediaService socialMediaService;

    @Operation(summary = "Get all social media links",
            description = "Returns a list of all social media links for all users.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SocialMedia.class)))
    @GetMapping
    public ResponseEntity<List<SocialMedia>> getAllSocialMedia() {
        return ResponseEntity.ok(socialMediaService.getAllSocialMedia());
    }

    @Operation(summary = "Get social media link by ID",
            description = "Returns a single social media link based on its ID.")
    @ApiResponse(responseCode = "200", description = "Link found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SocialMedia.class)))
    @ApiResponse(responseCode = "404", description = "Link not found")
    @GetMapping("/{id}")
    public ResponseEntity<SocialMedia> getSocialMediaById(
            @Parameter(description = "ID of the social media link to retrieve")
            @PathVariable String id) {
        return socialMediaService.getSocialMediaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get social media links by user details ID",
            description = "Returns a list of all social media links for a specific user details record.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SocialMedia.class)))
    @GetMapping("/user-detail/{userDetailId}")
    public ResponseEntity<List<SocialMedia>> getSocialMediaByUserDetail(
            @Parameter(description = "ID of the user details record")
            @PathVariable Long userDetailId) {
        return ResponseEntity.ok(socialMediaService.getSocialMediaByUserDetailId(userDetailId));
    }

    @Operation(summary = "Create a new social media link",
            description = "Creates a new social media link record.")
    @ApiResponse(responseCode = "200", description = "Link created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SocialMedia.class)))
    @PostMapping
    public ResponseEntity<SocialMedia> createSocialMedia(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new social media link", required = true)
            @RequestBody SocialMedia socialMedia) {
        return ResponseEntity.ok(socialMediaService.createSocialMedia(socialMedia));
    }

    @Operation(summary = "Update a social media link",
            description = "Updates an existing social media link based on its ID.")
    @ApiResponse(responseCode = "200", description = "Link updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SocialMedia.class)))
    @ApiResponse(responseCode = "404", description = "Link not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<SocialMedia> updateSocialMedia(
            @Parameter(description = "ID of the social media link to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated social media link details", required = true)
            @RequestBody SocialMedia socialMediaDetails) {
        try {
            SocialMedia updatedSocialMedia = socialMediaService.updateSocialMedia(id, socialMediaDetails);
            return ResponseEntity.ok(updatedSocialMedia);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a social media link",
            description = "Deletes a social media link based on its ID.")
    @ApiResponse(responseCode = "204", description = "Link deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocialMedia(
            @Parameter(description = "ID of the social media link to delete")
            @PathVariable String id) {
        socialMediaService.deleteSocialMedia(id);
        return ResponseEntity.noContent().build();
    }
}