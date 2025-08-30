package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Project;
import com.dreamers.the_dreamers.model.ProjectStatus;
import com.dreamers.the_dreamers.service.ProjectService;
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
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Project API", description = "API for managing charity projects")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Get all projects",
            description = "Returns a list of all charity projects.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class)))
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @Operation(summary = "Get projects with pagination",
            description = "Returns a paginated list of charity projects.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/page")
    public ResponseEntity<Page<Project>> getAllProjectsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of projects per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(projectService.getAllProjects(pageable));
    }

    @Operation(summary = "Get project by ID",
            description = "Returns a single project based on its ID.")
    @ApiResponse(responseCode = "200", description = "Project found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class)))
    @ApiResponse(responseCode = "404", description = "Project not found")
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(
            @Parameter(description = "ID of the project to retrieve")
            @PathVariable String id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get projects by status",
            description = "Returns a list of projects that match a specific status.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class)))
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(
            @Parameter(description = "Status of the project", example = "IN_PROGRESS")
            @PathVariable ProjectStatus status) {
        return ResponseEntity.ok(projectService.getProjectsByStatus(status));
    }

    @Operation(summary = "Get projects by status with pagination",
            description = "Returns a paginated list of projects that match a specific status.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/status/{status}/page")
    public ResponseEntity<Page<Project>> getProjectsByStatusPaginated(
            @Parameter(description = "Status of the project", example = "IN_PROGRESS")
            @PathVariable ProjectStatus status,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of projects per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(projectService.getProjectsByStatus(status, pageable));
    }

    @Operation(summary = "Get projects by collected amount",
            description = "Returns a list of projects that have collected a specific amount.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class)))
    @GetMapping("/collected-amount/{amount}")
    public ResponseEntity<List<Project>> getProjectsByCollectedAmount(
            @Parameter(description = "Collected amount to search for")
            @PathVariable double amount) {
        return ResponseEntity.ok(projectService.getProjectsByCollectedAmount(amount));
    }

    @Operation(summary = "Create a new project",
            description = "Creates a new project record.")
    @ApiResponse(responseCode = "200", description = "Project created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class)))
    @PostMapping
    public ResponseEntity<Project> createProject(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new project", required = true)
            @RequestBody Project project) {
        return ResponseEntity.ok(projectService.createProject(project));
    }

    @Operation(summary = "Update a project",
            description = "Updates an existing project based on its ID.")
    @ApiResponse(responseCode = "200", description = "Project updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Project.class)))
    @ApiResponse(responseCode = "404", description = "Project not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @Parameter(description = "ID of the project to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated project details", required = true)
            @RequestBody Project projectDetails) {
        try {
            Project updatedProject = projectService.updateProject(id, projectDetails);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a project",
            description = "Deletes a project record based on its ID.")
    @ApiResponse(responseCode = "204", description = "Project deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @Parameter(description = "ID of the project to delete")
            @PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}