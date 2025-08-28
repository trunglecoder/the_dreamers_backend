package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.ChatbotHistory;
import com.dreamers.the_dreamers.service.ChatbotHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chatbot-history")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChatbotHistoryController {
    
    private final ChatbotHistoryService chatbotHistoryService;
    
    @GetMapping
    public ResponseEntity<List<ChatbotHistory>> getAllChatbotHistories() {
        return ResponseEntity.ok(chatbotHistoryService.getAllChatbotHistories());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ChatbotHistory> getChatbotHistoryById(@PathVariable String id) {
        return chatbotHistoryService.getChatbotHistoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatbotHistory>> getChatbotHistoriesByUser(@PathVariable String userId) {
        return ResponseEntity.ok(chatbotHistoryService.getChatbotHistoriesByUserId(userId));
    }
    
    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<ChatbotHistory>> getChatbotHistoriesBySession(@PathVariable String sessionId) {
        return ResponseEntity.ok(chatbotHistoryService.getChatbotHistoriesBySessionId(sessionId));
    }
    
    @PostMapping
    public ResponseEntity<ChatbotHistory> createChatbotHistory(@RequestBody ChatbotHistory chatbotHistory) {
        return ResponseEntity.ok(chatbotHistoryService.createChatbotHistory(chatbotHistory));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ChatbotHistory> updateChatbotHistory(@PathVariable String id, @RequestBody ChatbotHistory chatbotHistoryDetails) {
        try {
            ChatbotHistory updatedChatbotHistory = chatbotHistoryService.updateChatbotHistory(id, chatbotHistoryDetails);
            return ResponseEntity.ok(updatedChatbotHistory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChatbotHistory(@PathVariable String id) {
        chatbotHistoryService.deleteChatbotHistory(id);
        return ResponseEntity.noContent().build();
    }
}
