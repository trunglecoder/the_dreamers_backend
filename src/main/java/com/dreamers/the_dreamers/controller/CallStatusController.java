package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.CallStatus;
import com.dreamers.the_dreamers.service.CallStatusService;
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
@RequestMapping("/api/call-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Call Status API", description = "API for managing video call statuses")
public class CallStatusController {

    private final CallStatusService callStatusService;

    @Operation(summary = "Get all call statuses",
            description = "Returns a list of all defined video call statuses.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CallStatus.class)))
    @GetMapping
    public ResponseEntity<List<CallStatus>> getAllCallStatuses() {
        return ResponseEntity.ok(callStatusService.getAllCallStatuses());
    }

    @Operation(summary = "Get call status by ID",
            description = "Returns a single call status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CallStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/{id}")
    public ResponseEntity<CallStatus> getCallStatusById(
            @Parameter(description = "ID of the call status to retrieve")
            @PathVariable String id) {
        return callStatusService.getCallStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get call status by name",
            description = "Returns a single call status based on its name.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CallStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/name/{statusName}")
    public ResponseEntity<CallStatus> getCallStatusByStatusName(
            @Parameter(description = "Name of the call status", example = "ONGOING")
            @PathVariable String statusName) {
        return callStatusService.getCallStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new call status",
            description = "Creates a new call status.")
    @ApiResponse(responseCode = "200", description = "Status created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CallStatus.class)))
    @PostMapping
    public ResponseEntity<CallStatus> createCallStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new call status", required = true)
            @RequestBody CallStatus callStatus) {
        return ResponseEntity.ok(callStatusService.createCallStatus(callStatus));
    }

    @Operation(summary = "Update a call status",
            description = "Updates an existing call status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CallStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<CallStatus> updateCallStatus(
            @Parameter(description = "ID of the call status to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated call status details", required = true)
            @RequestBody CallStatus callStatusDetails) {
        try {
            CallStatus updatedCallStatus = callStatusService.updateCallStatus(id, callStatusDetails);
            return ResponseEntity.ok(updatedCallStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a call status",
            description = "Deletes a call status based on its ID.")
    @ApiResponse(responseCode = "204", description = "Status deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCallStatus(
            @Parameter(description = "ID of the call status to delete")
            @PathVariable String id) {
        callStatusService.deleteCallStatus(id);
        return ResponseEntity.noContent().build();
    }
}