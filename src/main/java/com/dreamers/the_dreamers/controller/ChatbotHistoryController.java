package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ChatbotHistory;
import com.dreamers.the_dreamers.service.ChatbotHistoryService;
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
@RequestMapping("/api/chatbot-history")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Chatbot History API", description = "API for managing chatbot conversation history")
public class ChatbotHistoryController {

    private final ChatbotHistoryService chatbotHistoryService;

    @Operation(summary = "Get all chatbot history records",
            description = "Returns a list of all chatbot conversation history records.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChatbotHistory.class)))
    @GetMapping
    public ResponseEntity<List<ChatbotHistory>> getAllChatbotHistories() {
        return ResponseEntity.ok(chatbotHistoryService.getAllChatbotHistories());
    }

    @Operation(summary = "Get chatbot history record by ID",
            description = "Returns a single chatbot history record based on its ID.")
    @ApiResponse(responseCode = "200", description = "Record found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChatbotHistory.class)))
    @ApiResponse(responseCode = "404", description = "Record not found")
    @GetMapping("/{id}")
    public ResponseEntity<ChatbotHistory> getChatbotHistoryById(
            @Parameter(description = "ID of the chatbot history record to retrieve")
            @PathVariable String id) {
        return chatbotHistoryService.getChatbotHistoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get chatbot history by user ID",
            description = "Returns all chatbot history records for a specific user.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChatbotHistory.class)))
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatbotHistory>> getChatbotHistoriesByUser(
            @Parameter(description = "ID of the user")
            @PathVariable Long userId) {
        return ResponseEntity.ok(chatbotHistoryService.getChatbotHistoriesByUserId(userId));
    }

    @Operation(summary = "Get chatbot history by session ID",
            description = "Returns all chatbot history records for a specific session.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChatbotHistory.class)))
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<ChatbotHistory>> getChatbotHistoriesBySession(
            @Parameter(description = "ID of the conversation session")
            @PathVariable String sessionId) {
        return ResponseEntity.ok(chatbotHistoryService.getChatbotHistoriesBySessionId(sessionId));
    }

    @Operation(summary = "Create a new chatbot history record",
            description = "Creates a new record for a chatbot conversation.")
    @ApiResponse(responseCode = "200", description = "Record created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChatbotHistory.class)))
    @PostMapping
    public ResponseEntity<ChatbotHistory> createChatbotHistory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new chatbot history record", required = true)
            @RequestBody ChatbotHistory chatbotHistory) {
        return ResponseEntity.ok(chatbotHistoryService.createChatbotHistory(chatbotHistory));
    }

    @Operation(summary = "Update a chatbot history record",
            description = "Updates an existing chatbot history record based on its ID.")
    @ApiResponse(responseCode = "200", description = "Record updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ChatbotHistory.class)))
    @ApiResponse(responseCode = "404", description = "Record not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<ChatbotHistory> updateChatbotHistory(
            @Parameter(description = "ID of the chatbot history record to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated chatbot history details", required = true)
            @RequestBody ChatbotHistory chatbotHistoryDetails) {
        try {
            ChatbotHistory updatedChatbotHistory = chatbotHistoryService.updateChatbotHistory(id, chatbotHistoryDetails);
            return ResponseEntity.ok(updatedChatbotHistory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a chatbot history record",
            description = "Deletes a chatbot history record based on its ID.")
    @ApiResponse(responseCode = "204", description = "Record deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatbotHistory(
            @Parameter(description = "ID of the chatbot history record to delete")
            @PathVariable String id) {
        chatbotHistoryService.deleteChatbotHistory(id);
        return ResponseEntity.noContent().build();
    }
}