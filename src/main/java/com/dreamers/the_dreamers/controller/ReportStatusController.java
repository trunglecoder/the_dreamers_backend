package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ReportStatus;
import com.dreamers.the_dreamers.service.ReportStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportStatusController {
    
    private final ReportStatusService reportStatusService;
    
    @GetMapping
    public ResponseEntity<List<ReportStatus>> getAllReportStatuses() {
        return ResponseEntity.ok(reportStatusService.getAllReportStatuses());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReportStatus> getReportStatusById(@PathVariable String id) {
        return reportStatusService.getReportStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{statusName}")
    public ResponseEntity<ReportStatus> getReportStatusByStatusName(@PathVariable String statusName) {
        return reportStatusService.getReportStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ReportStatus> createReportStatus(@RequestBody ReportStatus reportStatus) {
        return ResponseEntity.ok(reportStatusService.createReportStatus(reportStatus));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ReportStatus> updateReportStatus(@PathVariable String id, @RequestBody ReportStatus reportStatusDetails) {
        try {
            ReportStatus updatedReportStatus = reportStatusService.updateReportStatus(id, reportStatusDetails);
            return ResponseEntity.ok(updatedReportStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportStatus(@PathVariable String id) {
        reportStatusService.deleteReportStatus(id);
        return ResponseEntity.noContent().build();
    }
}
