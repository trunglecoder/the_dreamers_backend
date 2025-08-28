package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.MessageType;
import com.dreamers.the_dreamers.service.MessageTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message-types")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MessageTypeController {
    
    private final MessageTypeService messageTypeService;
    
    @GetMapping
    public ResponseEntity<List<MessageType>> getAllMessageTypes() {
        return ResponseEntity.ok(messageTypeService.getAllMessageTypes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MessageType> getMessageTypeById(@PathVariable String id) {
        return messageTypeService.getMessageTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{typeName}")
    public ResponseEntity<MessageType> getMessageTypeByTypeName(@PathVariable String typeName) {
        return messageTypeService.getMessageTypeByTypeName(typeName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<MessageType> createMessageType(@RequestBody MessageType messageType) {
        return ResponseEntity.ok(messageTypeService.createMessageType(messageType));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MessageType> updateMessageType(@PathVariable String id, @RequestBody MessageType messageTypeDetails) {
        try {
            MessageType updatedMessageType = messageTypeService.updateMessageType(id, messageTypeDetails);
            return ResponseEntity.ok(updatedMessageType);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageType(@PathVariable String id) {
        messageTypeService.deleteMessageType(id);
        return ResponseEntity.noContent().build();
    }
}
