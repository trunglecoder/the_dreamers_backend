package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Message;
import com.dreamers.the_dreamers.service.MessageService;
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
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Message API", description = "API for managing messages and conversations")
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Get all messages",
            description = "Returns a list of all messages across all conversations.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class)))
    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @Operation(summary = "Get messages with pagination",
            description = "Returns a paginated list of messages.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/page")
    public ResponseEntity<Page<Message>> getAllMessagesPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of messages per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(messageService.getAllMessages(pageable));
    }

    @Operation(summary = "Get message by ID",
            description = "Returns a single message based on its ID.")
    @ApiResponse(responseCode = "200", description = "Message found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class)))
    @ApiResponse(responseCode = "404", description = "Message not found")
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(
            @Parameter(description = "ID of the message to retrieve")
            @PathVariable String id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get messages by conversation ID",
            description = "Returns all messages within a specific conversation.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class)))
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<Message>> getMessagesByConversation(
            @Parameter(description = "ID of the conversation")
            @PathVariable String conversationId) {
        return ResponseEntity.ok(messageService.getMessagesByConversationId(conversationId));
    }

    @Operation(summary = "Get paginated messages by conversation ID",
            description = "Returns a paginated list of messages within a specific conversation.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/conversation/{conversationId}/page")
    public ResponseEntity<Page<Message>> getMessagesByConversationPaginated(
            @Parameter(description = "ID of the conversation")
            @PathVariable String conversationId,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of messages per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(messageService.getMessagesByConversationId(conversationId, pageable));
    }

    @Operation(summary = "Get messages by sender ID",
            description = "Returns all messages sent by a specific user.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class)))
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<Message>> getMessagesBySender(
            @Parameter(description = "ID of the sender")
            @PathVariable String senderId) {
        return ResponseEntity.ok(messageService.getMessagesBySenderId(senderId));
    }

    @Operation(summary = "Create a new message",
            description = "Creates a new message record.")
    @ApiResponse(responseCode = "200", description = "Message created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class)))
    @PostMapping
    public ResponseEntity<Message> createMessage(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new message", required = true)
            @RequestBody Message message) {
        return ResponseEntity.ok(messageService.createMessage(message));
    }

    @Operation(summary = "Update a message",
            description = "Updates an existing message based on its ID.")
    @ApiResponse(responseCode = "200", description = "Message updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class)))
    @ApiResponse(responseCode = "404", description = "Message not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(
            @Parameter(description = "ID of the message to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated message details", required = true)
            @RequestBody Message messageDetails) {
        try {
            Message updatedMessage = messageService.updateMessage(id, messageDetails);
            return ResponseEntity.ok(updatedMessage);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a message",
            description = "Deletes a message record based on its ID.")
    @ApiResponse(responseCode = "204", description = "Message deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(
            @Parameter(description = "ID of the message to delete")
            @PathVariable String id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}