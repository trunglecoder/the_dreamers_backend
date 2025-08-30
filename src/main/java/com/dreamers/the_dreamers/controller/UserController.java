package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.User;
import com.dreamers.the_dreamers.service.UserService;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "User API", description = "API for managing users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all users",
            description = "Returns a list of all users.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)))
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get user by ID",
            description = "Returns a single user based on their ID.")
    @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID of the user to retrieve")
            @PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user by email",
            description = "Returns a single user based on their email address.")
    @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "Email of the user to retrieve")
            @PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user by username",
            description = "Returns a single user based on their username.")
    @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(
            @Parameter(description = "Username of the user to retrieve")
            @PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new user",
            description = "Creates a new user record.")
    @ApiResponse(responseCode = "200", description = "User created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)))
    @PostMapping
    public ResponseEntity<User> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new user", required = true)
            @RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @Operation(summary = "Update a user",
            description = "Updates an existing user based on their ID.")
    @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "404", description = "User not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID of the user to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated user details", required = true)
            @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a user",
            description = "Deletes a user based on their ID.")
    @ApiResponse(responseCode = "204", description = "User deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete")
            @PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check if email exists",
            description = "Checks if a user with the given email address already exists.")
    @ApiResponse(responseCode = "200", description = "Returns true if email exists, false otherwise")
    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(
            @Parameter(description = "Email address to check")
            @PathVariable String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

    @Operation(summary = "Check if username exists",
            description = "Checks if a user with the given username already exists.")
    @ApiResponse(responseCode = "200", description = "Returns true if username exists, false otherwise")
    @GetMapping("/check-username/{username}")
    public ResponseEntity<Boolean> checkUsernameExists(
            @Parameter(description = "Username to check")
            @PathVariable String username) {
        return ResponseEntity.ok(userService.existsByUsername(username));
    }
}