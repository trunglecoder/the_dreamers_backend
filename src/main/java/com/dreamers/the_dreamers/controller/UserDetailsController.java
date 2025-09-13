package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.UserDetails;
import com.dreamers.the_dreamers.service.UserDetailsService;
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
@RequestMapping("/api/user-details")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "User Details API", description = "API for managing user details")
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    @Operation(summary = "Get all user details",
            description = "Returns a list of all user detail records.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDetails.class)))
    @GetMapping
    public ResponseEntity<List<UserDetails>> getAllUserDetails() {
        return ResponseEntity.ok(userDetailsService.getAllUserDetails());
    }

    @Operation(summary = "Get user details by ID",
            description = "Returns a single user details record based on its ID.")
    @ApiResponse(responseCode = "200", description = "User details found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDetails.class)))
    @ApiResponse(responseCode = "404", description = "User details not found")
    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserDetailsById(
            @Parameter(description = "ID of the user details record to retrieve")
            @PathVariable String id) {
        return userDetailsService.getUserDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user details by user ID",
            description = "Returns a single user details record based on the associated user's ID.")
    @ApiResponse(responseCode = "200", description = "User details found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDetails.class)))
    @ApiResponse(responseCode = "404", description = "User details not found")
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDetails> getUserDetailsByUserId(
            @Parameter(description = "ID of the associated user")
            @PathVariable Long userId) {
        return userDetailsService.getUserDetailsByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create new user details",
            description = "Creates a new user details record.")
    @ApiResponse(responseCode = "200", description = "User details created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDetails.class)))
    @PostMapping
    public ResponseEntity<UserDetails> createUserDetails(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new user details record", required = true)
            @RequestBody UserDetails userDetails) {
        return ResponseEntity.ok(userDetailsService.createUserDetails(userDetails));
    }

    @Operation(summary = "Update user details",
            description = "Updates an existing user details record based on its ID.")
    @ApiResponse(responseCode = "200", description = "User details updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserDetails.class)))
    @ApiResponse(responseCode = "404", description = "User details not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<UserDetails> updateUserDetails(
            @Parameter(description = "ID of the user details record to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated user details", required = true)
            @RequestBody UserDetails userDetailsDetails) {
        try {
            UserDetails updatedUserDetails = userDetailsService.updateUserDetails(id, userDetailsDetails);
            return ResponseEntity.ok(updatedUserDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete user details",
            description = "Deletes a user details record based on its ID.")
    @ApiResponse(responseCode = "204", description = "User details deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserDetails(
            @Parameter(description = "ID of the user details record to delete")
            @PathVariable String id) {
        userDetailsService.deleteUserDetails(id);
        return ResponseEntity.noContent().build();
    }
}