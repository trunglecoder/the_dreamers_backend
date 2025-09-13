package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Message;
import com.dreamers.the_dreamers.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    
    private final MessageRepository messageRepository;
    
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    public Page<Message> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }
    
    public Optional<Message> getMessageById(String id) {
        return messageRepository.findById(id);
    }
    
    public List<Message> getMessagesByConversationId(Long conversationId) {
        return messageRepository.findByConversationIdOrderBySentAtAsc(conversationId);
    }
    
    public Page<Message> getMessagesByConversationId(Long conversationId, Pageable pageable) {
        return messageRepository.findByConversationIdOrderBySentAtDesc(conversationId, pageable);
    }
    
    public List<Message> getMessagesBySenderId(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }
    
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
    
    public Message updateMessage(String id, Message messageDetails) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        
        message.setContent(messageDetails.getContent());
        message.setSentAt(messageDetails.getSentAt());
        message.setIsRead(messageDetails.getIsRead());
        message.setMessageType(messageDetails.getMessageType());
        
        return messageRepository.save(message);
    }
    
    public void deleteMessage(String id) {
        messageRepository.deleteById(id);
    }
}
