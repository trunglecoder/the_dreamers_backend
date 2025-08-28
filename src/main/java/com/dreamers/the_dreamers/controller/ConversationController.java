package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Conversation;
import com.dreamers.the_dreamers.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConversationController {
    
    private final ConversationService conversationService;
    
    @GetMapping
    public ResponseEntity<List<Conversation>> getAllConversations() {
        return ResponseEntity.ok(conversationService.getAllConversations());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Conversation> getConversationById(@PathVariable String id) {
        return conversationService.getConversationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<Conversation>> getConversationsByParticipant(@PathVariable String participantId) {
        return ResponseEntity.ok(conversationService.getConversationsByParticipant(participantId));
    }
    
    @PostMapping
    public ResponseEntity<Conversation> createConversation(@RequestBody Conversation conversation) {
        return ResponseEntity.ok(conversationService.createConversation(conversation));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Conversation> updateConversation(@PathVariable String id, @RequestBody Conversation conversationDetails) {
        try {
            Conversation updatedConversation = conversationService.updateConversation(id, conversationDetails);
            return ResponseEntity.ok(updatedConversation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConversation(@PathVariable String id) {
        conversationService.deleteConversation(id);
        return ResponseEntity.noContent().build();
    }
}
