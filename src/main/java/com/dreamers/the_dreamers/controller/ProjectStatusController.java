package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ProjectStatus;
import com.dreamers.the_dreamers.service.ProjectStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectStatusController {
    
    private final ProjectStatusService projectStatusService;
    
    @GetMapping
    public ResponseEntity<List<ProjectStatus>> getAllProjectStatuses() {
        return ResponseEntity.ok(projectStatusService.getAllProjectStatuses());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProjectStatus> getProjectStatusById(@PathVariable String id) {
        return projectStatusService.getProjectStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{statusName}")
    public ResponseEntity<ProjectStatus> getProjectStatusByStatusName(@PathVariable String statusName) {
        return projectStatusService.getProjectStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ProjectStatus> createProjectStatus(@RequestBody ProjectStatus projectStatus) {
        return ResponseEntity.ok(projectStatusService.createProjectStatus(projectStatus));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProjectStatus> updateProjectStatus(@PathVariable String id, @RequestBody ProjectStatus projectStatusDetails) {
        try {
            ProjectStatus updatedProjectStatus = projectStatusService.updateProjectStatus(id, projectStatusDetails);
            return ResponseEntity.ok(updatedProjectStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectStatus(@PathVariable String id) {
        projectStatusService.deleteProjectStatus(id);
        return ResponseEntity.noContent().build();
    }
}
