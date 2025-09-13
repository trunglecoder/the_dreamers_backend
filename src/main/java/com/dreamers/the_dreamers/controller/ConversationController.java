package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Conversation;
import com.dreamers.the_dreamers.service.ConversationService;
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
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Conversation API", description = "API for managing conversations between users")
public class ConversationController {

    private final ConversationService conversationService;

    @Operation(summary = "Get all conversations",
            description = "Returns a list of all conversations.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Conversation.class)))
    @GetMapping
    public ResponseEntity<List<Conversation>> getAllConversations() {
        return ResponseEntity.ok(conversationService.getAllConversations());
    }

    @Operation(summary = "Get conversation by ID",
            description = "Returns a single conversation based on its ID.")
    @ApiResponse(responseCode = "200", description = "Conversation found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Conversation.class)))
    @ApiResponse(responseCode = "404", description = "Conversation not found")
    @GetMapping("/{id}")
    public ResponseEntity<Conversation> getConversationById(
            @Parameter(description = "ID of the conversation to retrieve")
            @PathVariable String id) {
        return conversationService.getConversationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get conversations by participant ID",
            description = "Returns all conversations involving a specific participant.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Conversation.class)))
    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<Conversation>> getConversationsByParticipant(
            @Parameter(description = "ID of the participant")
            @PathVariable Long participantId) {
        return ResponseEntity.ok(conversationService.getConversationsByParticipant(participantId));
    }

    @Operation(summary = "Create a new conversation",
            description = "Creates a new conversation between two users.")
    @ApiResponse(responseCode = "200", description = "Conversation created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Conversation.class)))
    @PostMapping
    public ResponseEntity<Conversation> createConversation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new conversation", required = true)
            @RequestBody Conversation conversation) {
        return ResponseEntity.ok(conversationService.createConversation(conversation));
    }

    @Operation(summary = "Update a conversation",
            description = "Updates an existing conversation based on its ID.")
    @ApiResponse(responseCode = "200", description = "Conversation updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Conversation.class)))
    @ApiResponse(responseCode = "404", description = "Conversation not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Conversation> updateConversation(
            @Parameter(description = "ID of the conversation to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated conversation details", required = true)
            @RequestBody Conversation conversationDetails) {
        try {
            Conversation updatedConversation = conversationService.updateConversation(id, conversationDetails);
            return ResponseEntity.ok(updatedConversation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a conversation",
            description = "Deletes a conversation based on its ID.")
    @ApiResponse(responseCode = "204", description = "Conversation deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversation(
            @Parameter(description = "ID of the conversation to delete")
            @PathVariable String id) {
        conversationService.deleteConversation(id);
        return ResponseEntity.noContent().build();
    }
}