package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ReportType;
import com.dreamers.the_dreamers.service.ReportTypeService;
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
@RequestMapping("/api/report-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Report Type API", description = "API for managing report types")
public class ReportTypeController {

    private final ReportTypeService reportTypeService;

    @Operation(summary = "Get all report types",
            description = "Returns a list of all defined report types.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportType.class)))
    @GetMapping
    public ResponseEntity<List<ReportType>> getAllReportTypes() {
        return ResponseEntity.ok(reportTypeService.getAllReportTypes());
    }

    @Operation(summary = "Get report type by ID",
            description = "Returns a single report type based on its ID.")
    @ApiResponse(responseCode = "200", description = "Type found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found")
    @GetMapping("/{id}")
    public ResponseEntity<ReportType> getReportTypeById(
            @Parameter(description = "ID of the report type to retrieve")
            @PathVariable String id) {
        return reportTypeService.getReportTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get report type by name",
            description = "Returns a single report type based on its name.")
    @ApiResponse(responseCode = "200", description = "Type found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found")
    @GetMapping("/name/{typeName}")
    public ResponseEntity<ReportType> getReportTypeByTypeName(
            @Parameter(description = "Name of the report type", example = "Spam")
            @PathVariable String typeName) {
        return reportTypeService.getReportTypeByTypeName(typeName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new report type",
            description = "Creates a new report type.")
    @ApiResponse(responseCode = "200", description = "Type created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportType.class)))
    @PostMapping
    public ResponseEntity<ReportType> createReportType(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new report type", required = true)
            @RequestBody ReportType reportType) {
        return ResponseEntity.ok(reportTypeService.createReportType(reportType));
    }

    @Operation(summary = "Update a report type",
            description = "Updates an existing report type based on its ID.")
    @ApiResponse(responseCode = "200", description = "Type updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ReportType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<ReportType> updateReportType(
            @Parameter(description = "ID of the report type to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated report type details", required = true)
            @RequestBody ReportType reportTypeDetails) {
        try {
            ReportType updatedReportType = reportTypeService.updateReportType(id, reportTypeDetails);
            return ResponseEntity.ok(updatedReportType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a report type",
            description = "Deletes a report type based on its ID.")
    @ApiResponse(responseCode = "204", description = "Type deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportType(
            @Parameter(description = "ID of the report type to delete")
            @PathVariable String id) {
        reportTypeService.deleteReportType(id);
        return ResponseEntity.noContent().build();
    }
}