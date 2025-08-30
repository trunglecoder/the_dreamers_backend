package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.CallParticipant;
import com.dreamers.the_dreamers.service.CallParticipantService;
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
@RequestMapping("/api/call-participants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Call Participant API", description = "API for managing video call participants")
public class CallParticipantController {

    private final CallParticipantService callParticipantService;

    @Operation(summary = "Get all call participants",
            description = "Returns a list of all participants across all video calls.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CallParticipant.class)))
    @GetMapping
    public ResponseEntity<List<CallParticipant>> getAllCallParticipants() {
        return ResponseEntity.ok(callParticipantService.getAllCallParticipants());
    }

    @Operation(summary = "Get call participant by ID",
            description = "Returns a single call participant based on their ID.")
    @ApiResponse(responseCode = "200", description = "Participant found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CallParticipant.class)))
    @ApiResponse(responseCode = "404", description = "Participant not found")
    @GetMapping("/{id}")
    public ResponseEntity<CallParticipant> getCallParticipantById(
            @Parameter(description = "ID of the call participant to retrieve")
            @PathVariable String id) {
        return callParticipantService.getCallParticipantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new call participant",
            description = "Creates a new record for a participant in a video call.")
    @ApiResponse(responseCode = "200", description = "Participant created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CallParticipant.class)))
    @PostMapping
    public ResponseEntity<CallParticipant> createCallParticipant(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new call participant", required = true)
            @RequestBody CallParticipant callParticipant) {
        return ResponseEntity.ok(callParticipantService.createCallParticipant(callParticipant));
    }

    @Operation(summary = "Update a call participant",
            description = "Updates an existing call participant's details based on their ID.")
    @ApiResponse(responseCode = "200", description = "Participant updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CallParticipant.class)))
    @ApiResponse(responseCode = "404", description = "Participant not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<CallParticipant> updateCallParticipant(
            @Parameter(description = "ID of the call participant to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated call participant details", required = true)
            @RequestBody CallParticipant callParticipantDetails) {
        try {
            CallParticipant updatedCallParticipant = callParticipantService.updateCallParticipant(id, callParticipantDetails);
            return ResponseEntity.ok(updatedCallParticipant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a call participant",
            description = "Deletes a call participant based on their ID.")
    @ApiResponse(responseCode = "204", description = "Participant deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCallParticipant(
            @Parameter(description = "ID of the call participant to delete")
            @PathVariable String id) {
        callParticipantService.deleteCallParticipant(id);
        return ResponseEntity.noContent().build();
    }
}