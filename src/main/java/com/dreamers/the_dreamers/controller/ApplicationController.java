package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Application;
import com.dreamers.the_dreamers.model.ApplicationStatus;
import com.dreamers.the_dreamers.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Application API", description = "API for managing member applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "Get all applications",
            description = "Returns a list of all member applications.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Application.class)))
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @Operation(summary = "Get applications with pagination",
            description = "Returns a list of applications with custom page and size.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/page")
    public ResponseEntity<Page<Application>> getAllApplicationsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of applications per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(applicationService.getAllApplications(pageable));
    }

    @Operation(summary = "Get application by ID",
            description = "Returns a single application based on its ID.")
    @ApiResponse(responseCode = "200", description = "Application found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Application.class)))
    @ApiResponse(responseCode = "404", description = "Application not found")
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(
            @Parameter(description = "ID of the application to retrieve", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
            @PathVariable String id) {
        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get applications by user ID",
            description = "Returns all applications submitted by a specific user.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Application.class)))
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getApplicationsByUser(
            @Parameter(description = "ID of the user", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
            @PathVariable Long userId) {
        return ResponseEntity.ok(applicationService.getApplicationsByUserId(userId));
    }

    @Operation(summary = "Get applications by status",
            description = "Returns applications that match a specific status (e.g., PENDING, ACCEPTED).")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Application.class)))
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(
            @Parameter(description = "Status of the application", example = "PENDING")
            @PathVariable ApplicationStatus status) {
        return ResponseEntity.ok(applicationService.getApplicationsByStatus(status));
    }

    @Operation(summary = "Get applications by status with pagination",
            description = "Returns applications that match a specific status with custom page and size.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/status/{status}/page")
    public ResponseEntity<Page<Application>> getApplicationsByStatusPaginated(
            @Parameter(description = "Status of the application", example = "PENDING")
            @PathVariable ApplicationStatus status,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of applications per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(applicationService.getApplicationsByStatus(status, pageable));
    }

    @Operation(summary = "Create a new application",
            description = "Creates a new member application.")
    @ApiResponse(responseCode = "200", description = "Application created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Application.class)))
    @PostMapping
    public ResponseEntity<Application> createApplication(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Application details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Application.class)))
            @RequestBody Application application) {
        return ResponseEntity.ok(applicationService.createApplication(application));
    }

    @Operation(summary = "Update an application",
            description = "Updates an existing application based on its ID.")
    @ApiResponse(responseCode = "200", description = "Application updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Application.class)))
    @ApiResponse(responseCode = "404", description = "Application not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(
            @Parameter(description = "ID of the application to update", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated application details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Application.class)))
            @RequestBody Application applicationDetails) {
        try {
            Application updatedApplication = applicationService.updateApplication(id, applicationDetails);
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an application",
            description = "Deletes an application based on its ID.")
    @ApiResponse(responseCode = "204", description = "Application deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(
            @Parameter(description = "ID of the application to delete", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
            @PathVariable String id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}