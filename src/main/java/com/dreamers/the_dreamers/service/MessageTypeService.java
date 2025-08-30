package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.MessageType;
import com.dreamers.the_dreamers.repository.MessageTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageTypeService {
    
    private final MessageTypeRepository messageTypeRepository;
    
    public List<MessageType> getAllMessageTypes() {
        return messageTypeRepository.findAll();
    }
    
    public Optional<MessageType> getMessageTypeById(String id) {
        return messageTypeRepository.findById(id);
    }
    
    public Optional<MessageType> getMessageTypeByTypeName(String typeName) {
        return messageTypeRepository.findByTypeName(typeName);
    }
    
    public MessageType createMessageType(MessageType messageType) {
        return messageTypeRepository.save(messageType);
    }
    
    public MessageType updateMessageType(String id, MessageType messageTypeDetails) {
        MessageType messageType = messageTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MessageType not found"));
        
        messageType.setTypeName(messageTypeDetails.getTypeName());
        
        return messageTypeRepository.save(messageType);
    }
    
    public void deleteMessageType(String id) {
        messageTypeRepository.deleteById(id);
    }
}
