package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ProjectStatus;
import com.dreamers.the_dreamers.service.ProjectStatusService;
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
@RequestMapping("/api/project-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Project Status API", description = "API for managing project statuses")
public class ProjectStatusController {

    private final ProjectStatusService projectStatusService;

    @Operation(summary = "Get all project statuses",
            description = "Returns a list of all defined project statuses.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectStatus.class)))
    @GetMapping
    public ResponseEntity<List<ProjectStatus>> getAllProjectStatuses() {
        return ResponseEntity.ok(projectStatusService.getAllProjectStatuses());
    }

    @Operation(summary = "Get project status by ID",
            description = "Returns a single project status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectStatus> getProjectStatusById(
            @Parameter(description = "ID of the project status to retrieve")
            @PathVariable String id) {
        return projectStatusService.getProjectStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get project status by name",
            description = "Returns a single project status based on its name.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/name/{statusName}")
    public ResponseEntity<ProjectStatus> getProjectStatusByStatusName(
            @Parameter(description = "Name of the project status", example = "IN_PROGRESS")
            @PathVariable String statusName) {
        return projectStatusService.getProjectStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new project status",
            description = "Creates a new project status.")
    @ApiResponse(responseCode = "200", description = "Status created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectStatus.class)))
    @PostMapping
    public ResponseEntity<ProjectStatus> createProjectStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new project status", required = true)
            @RequestBody ProjectStatus projectStatus) {
        return ResponseEntity.ok(projectStatusService.createProjectStatus(projectStatus));
    }

    @Operation(summary = "Update a project status",
            description = "Updates an existing project status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProjectStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectStatus> updateProjectStatus(
            @Parameter(description = "ID of the project status to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated project status details", required = true)
            @RequestBody ProjectStatus projectStatusDetails) {
        try {
            ProjectStatus updatedProjectStatus = projectStatusService.updateProjectStatus(id, projectStatusDetails);
            return ResponseEntity.ok(updatedProjectStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a project status",
            description = "Deletes a project status based on its ID.")
    @ApiResponse(responseCode = "204", description = "Status deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectStatus(
            @Parameter(description = "ID of the project status to delete")
            @PathVariable String id) {
        projectStatusService.deleteProjectStatus(id);
        return ResponseEntity.noContent().build();
    }
}