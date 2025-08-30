package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Report;
import com.dreamers.the_dreamers.model.ReportStatus;
import com.dreamers.the_dreamers.service.ReportService;
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
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Report API", description = "API for managing user reports and complaints")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Get all reports",
            description = "Returns a list of all reports.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Report.class)))
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @Operation(summary = "Get reports with pagination",
            description = "Returns a paginated list of all reports.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/page")
    public ResponseEntity<Page<Report>> getAllReportsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of reports per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(reportService.getAllReports(pageable));
    }

    @Operation(summary = "Get report by ID",
            description = "Returns a single report based on its ID.")
    @ApiResponse(responseCode = "200", description = "Report found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Report.class)))
    @ApiResponse(responseCode = "404", description = "Report not found")
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(
            @Parameter(description = "ID of the report to retrieve")
            @PathVariable String id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get reports by reporter ID",
            description = "Returns a list of all reports submitted by a specific user.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Report.class)))
    @GetMapping("/reporter/{reporterId}")
    public ResponseEntity<List<Report>> getReportsByReporter(
            @Parameter(description = "ID of the reporter")
            @PathVariable String reporterId) {
        return ResponseEntity.ok(reportService.getReportsByReporterId(reporterId));
    }

    @Operation(summary = "Get reports by status",
            description = "Returns a list of reports that match a specific status.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Report.class)))
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Report>> getReportsByStatus(
            @Parameter(description = "Status of the report (e.g., PENDING, RESOLVED)")
            @PathVariable ReportStatus status) {
        return ResponseEntity.ok(reportService.getReportsByStatus(status));
    }

    @Operation(summary = "Get reports by status with pagination",
            description = "Returns a paginated list of reports that match a specific status.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/status/{status}/page")
    public ResponseEntity<Page<Report>> getReportsByStatusPaginated(
            @Parameter(description = "Status of the report (e.g., PENDING, RESOLVED)")
            @PathVariable ReportStatus status,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of reports per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(reportService.getReportsByStatus(status, pageable));
    }

    @Operation(summary = "Create a new report",
            description = "Creates a new report record.")
    @ApiResponse(responseCode = "200", description = "Report created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Report.class)))
    @PostMapping
    public ResponseEntity<Report> createReport(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new report", required = true)
            @RequestBody Report report) {
        return ResponseEntity.ok(reportService.createReport(report));
    }

    @Operation(summary = "Update a report",
            description = "Updates an existing report based on its ID.")
    @ApiResponse(responseCode = "200", description = "Report updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Report.class)))
    @ApiResponse(responseCode = "404", description = "Report not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(
            @Parameter(description = "ID of the report to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated report details", required = true)
            @RequestBody Report reportDetails) {
        try {
            Report updatedReport = reportService.updateReport(id, reportDetails);
            return ResponseEntity.ok(updatedReport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a report",
            description = "Deletes a report based on its ID.")
    @ApiResponse(responseCode = "204", description = "Report deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(
            @Parameter(description = "ID of the report to delete")
            @PathVariable String id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }
}