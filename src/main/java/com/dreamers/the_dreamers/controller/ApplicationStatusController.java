package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ApplicationStatus;
import com.dreamers.the_dreamers.service.ApplicationStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApplicationStatusController {
    
    private final ApplicationStatusService applicationStatusService;
    
    @GetMapping
    public ResponseEntity<List<ApplicationStatus>> getAllApplicationStatuses() {
        return ResponseEntity.ok(applicationStatusService.getAllApplicationStatuses());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationStatus> getApplicationStatusById(@PathVariable String id) {
        return applicationStatusService.getApplicationStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{statusName}")
    public ResponseEntity<ApplicationStatus> getApplicationStatusByStatusName(@PathVariable String statusName) {
        return applicationStatusService.getApplicationStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ApplicationStatus> createApplicationStatus(@RequestBody ApplicationStatus applicationStatus) {
        return ResponseEntity.ok(applicationStatusService.createApplicationStatus(applicationStatus));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationStatus> updateApplicationStatus(@PathVariable String id, @RequestBody ApplicationStatus applicationStatusDetails) {
        try {
            ApplicationStatus updatedApplicationStatus = applicationStatusService.updateApplicationStatus(id, applicationStatusDetails);
            return ResponseEntity.ok(updatedApplicationStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicationStatus(@PathVariable String id) {
        applicationStatusService.deleteApplicationStatus(id);
        return ResponseEntity.noContent().build();
    }
}
