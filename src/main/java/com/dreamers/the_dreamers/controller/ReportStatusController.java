package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ReportStatus;
import com.dreamers.the_dreamers.service.ReportStatusService;
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
@RequestMapping("/api/report-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Report Status API", description = "API for managing report statuses")
public class ReportStatusController {

    private final ReportStatusService reportStatusService;

    @Operation(summary = "Get all report statuses",
            description = "Returns a list of all defined report statuses.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportStatus.class)))
    @GetMapping
    public ResponseEntity<List<ReportStatus>> getAllReportStatuses() {
        return ResponseEntity.ok(reportStatusService.getAllReportStatuses());
    }

    @Operation(summary = "Get report status by ID",
            description = "Returns a single report status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/{id}")
    public ResponseEntity<ReportStatus> getReportStatusById(
            @Parameter(description = "ID of the report status to retrieve")
            @PathVariable String id) {
        return reportStatusService.getReportStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get report status by name",
            description = "Returns a single report status based on its name.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/name/{statusName}")
    public ResponseEntity<ReportStatus> getReportStatusByStatusName(
            @Parameter(description = "Name of the report status", example = "PENDING")
            @PathVariable String statusName) {
        return reportStatusService.getReportStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new report status",
            description = "Creates a new report status.")
    @ApiResponse(responseCode = "200", description = "Status created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportStatus.class)))
    @PostMapping
    public ResponseEntity<ReportStatus> createReportStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new report status", required = true)
            @RequestBody ReportStatus reportStatus) {
        return ResponseEntity.ok(reportStatusService.createReportStatus(reportStatus));
    }

    @Operation(summary = "Update a report status",
            description = "Updates an existing report status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<ReportStatus> updateReportStatus(
            @Parameter(description = "ID of the report status to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated report status details", required = true)
            @RequestBody ReportStatus reportStatusDetails) {
        try {
            ReportStatus updatedReportStatus = reportStatusService.updateReportStatus(id, reportStatusDetails);
            return ResponseEntity.ok(updatedReportStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a report status",
            description = "Deletes a report status based on its ID.")
    @ApiResponse(responseCode = "204", description = "Status deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportStatus(
            @Parameter(description = "ID of the report status to delete")
            @PathVariable String id) {
        reportStatusService.deleteReportStatus(id);
        return ResponseEntity.noContent().build();
    }
}