package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ReportType;
import com.dreamers.the_dreamers.service.ReportTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportTypeController {
    
    private final ReportTypeService reportTypeService;
    
    @GetMapping
    public ResponseEntity<List<ReportType>> getAllReportTypes() {
        return ResponseEntity.ok(reportTypeService.getAllReportTypes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ReportType> getReportTypeById(@PathVariable String id) {
        return reportTypeService.getReportTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{typeName}")
    public ResponseEntity<ReportType> getReportTypeByTypeName(@PathVariable String typeName) {
        return reportTypeService.getReportTypeByTypeName(typeName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ReportType> createReportType(@RequestBody ReportType reportType) {
        return ResponseEntity.ok(reportTypeService.createReportType(reportType));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ReportType> updateReportType(@PathVariable String id, @RequestBody ReportType reportTypeDetails) {
        try {
            ReportType updatedReportType = reportTypeService.updateReportType(id, reportTypeDetails);
            return ResponseEntity.ok(updatedReportType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportType(@PathVariable String id) {
        reportTypeService.deleteReportType(id);
        return ResponseEntity.noContent().build();
    }
}
