package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Conversation;
import com.dreamers.the_dreamers.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationService {
    
    private final ConversationRepository conversationRepository;
    
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }
    
    public Optional<Conversation> getConversationById(String id) {
        return conversationRepository.findById(id);
    }
    
    public List<Conversation> getConversationsByParticipant(String participantId) {
        return conversationRepository.findByParticipant1IdOrParticipant2Id(participantId, participantId);
    }
    
    public Conversation createConversation(Conversation conversation) {
        return conversationRepository.save(conversation);
    }
    
    public Conversation updateConversation(String id, Conversation conversationDetails) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        
        conversation.setParticipant1(conversationDetails.getParticipant1());
        conversation.setParticipant2(conversationDetails.getParticipant2());
        
        return conversationRepository.save(conversation);
    }
    
    public void deleteConversation(String id) {
        conversationRepository.deleteById(id);
    }
}
