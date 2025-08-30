package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.MessageType;
import com.dreamers.the_dreamers.service.MessageTypeService;
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
@RequestMapping("/api/message-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Message Type API", description = "API for managing message types")
public class MessageTypeController {

    private final MessageTypeService messageTypeService;

    @Operation(summary = "Get all message types",
            description = "Returns a list of all defined message types.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageType.class)))
    @GetMapping
    public ResponseEntity<List<MessageType>> getAllMessageTypes() {
        return ResponseEntity.ok(messageTypeService.getAllMessageTypes());
    }

    @Operation(summary = "Get message type by ID",
            description = "Returns a single message type based on its ID.")
    @ApiResponse(responseCode = "200", description = "Type found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found")
    @GetMapping("/{id}")
    public ResponseEntity<MessageType> getMessageTypeById(
            @Parameter(description = "ID of the message type to retrieve")
            @PathVariable String id) {
        return messageTypeService.getMessageTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get message type by name",
            description = "Returns a single message type based on its name.")
    @ApiResponse(responseCode = "200", description = "Type found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found")
    @GetMapping("/name/{typeName}")
    public ResponseEntity<MessageType> getMessageTypeByTypeName(
            @Parameter(description = "Name of the message type", example = "text")
            @PathVariable String typeName) {
        return messageTypeService.getMessageTypeByTypeName(typeName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new message type",
            description = "Creates a new message type.")
    @ApiResponse(responseCode = "200", description = "Type created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageType.class)))
    @PostMapping
    public ResponseEntity<MessageType> createMessageType(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new message type", required = true)
            @RequestBody MessageType messageType) {
        return ResponseEntity.ok(messageTypeService.createMessageType(messageType));
    }

    @Operation(summary = "Update a message type",
            description = "Updates an existing message type based on its ID.")
    @ApiResponse(responseCode = "200", description = "Type updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MessageType.class)))
    @ApiResponse(responseCode = "404", description = "Type not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<MessageType> updateMessageType(
            @Parameter(description = "ID of the message type to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated message type details", required = true)
            @RequestBody MessageType messageTypeDetails) {
        try {
            MessageType updatedMessageType = messageTypeService.updateMessageType(id, messageTypeDetails);
            return ResponseEntity.ok(updatedMessageType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a message type",
            description = "Deletes a message type based on its ID.")
    @ApiResponse(responseCode = "204", description = "Type deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageType(
            @Parameter(description = "ID of the message type to delete")
            @PathVariable String id) {
        messageTypeService.deleteMessageType(id);
        return ResponseEntity.noContent().build();
    }
}