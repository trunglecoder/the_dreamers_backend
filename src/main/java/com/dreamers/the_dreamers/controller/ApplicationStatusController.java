package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ApplicationStatus;
import com.dreamers.the_dreamers.service.ApplicationStatusService;
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
@RequestMapping("/api/application-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Application Status API", description = "API for managing application statuses")
public class ApplicationStatusController {

    private final ApplicationStatusService applicationStatusService;

    @Operation(summary = "Get all application statuses",
            description = "Returns a list of all defined application statuses.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationStatus.class)))
    @GetMapping
    public ResponseEntity<List<ApplicationStatus>> getAllApplicationStatuses() {
        return ResponseEntity.ok(applicationStatusService.getAllApplicationStatuses());
    }

    @Operation(summary = "Get application status by ID",
            description = "Returns a single application status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationStatus> getApplicationStatusById(
            @Parameter(description = "ID of the application status to retrieve")
            @PathVariable String id) {
        return applicationStatusService.getApplicationStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get application status by name",
            description = "Returns a single application status based on its name.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/name/{statusName}")
    public ResponseEntity<ApplicationStatus> getApplicationStatusByStatusName(
            @Parameter(description = "Name of the application status", example = "PENDING")
            @PathVariable String statusName) {
        return applicationStatusService.getApplicationStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new application status",
            description = "Creates a new application status.")
    @ApiResponse(responseCode = "200", description = "Status created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationStatus.class)))
    @PostMapping
    public ResponseEntity<ApplicationStatus> createApplicationStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new application status",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ApplicationStatus.class)))
            @RequestBody ApplicationStatus applicationStatus) {
        return ResponseEntity.ok(applicationStatusService.createApplicationStatus(applicationStatus));
    }

    @Operation(summary = "Update an application status",
            description = "Updates an existing application status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApplicationStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationStatus> updateApplicationStatus(
            @Parameter(description = "ID of the application status to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated application status details",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ApplicationStatus.class)))
            @RequestBody ApplicationStatus applicationStatusDetails) {
        try {
            ApplicationStatus updatedApplicationStatus = applicationStatusService.updateApplicationStatus(id, applicationStatusDetails);
            return ResponseEntity.ok(updatedApplicationStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete an application status",
            description = "Deletes an application status based on its ID.")
    @ApiResponse(responseCode = "204", description = "Status deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicationStatus(
            @Parameter(description = "ID of the application status to delete")
            @PathVariable String id) {
        applicationStatusService.deleteApplicationStatus(id);
        return ResponseEntity.noContent().build();
    }
}