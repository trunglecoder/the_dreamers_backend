package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.ChatbotHistory;
import com.dreamers.the_dreamers.repository.ChatbotHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatbotHistoryService {
    
    private final ChatbotHistoryRepository chatbotHistoryRepository;
    
    public List<ChatbotHistory> getAllChatbotHistories() {
        return chatbotHistoryRepository.findAll();
    }
    
    public Optional<ChatbotHistory> getChatbotHistoryById(String id) {
        return chatbotHistoryRepository.findById(id);
    }
    
    public List<ChatbotHistory> getChatbotHistoriesByUserId(Long userId) {
        return chatbotHistoryRepository.findByUserId(userId);
    }
    
    public List<ChatbotHistory> getChatbotHistoriesBySessionId(String sessionId) {
        return chatbotHistoryRepository.findBySessionId(sessionId);
    }
    
    public ChatbotHistory createChatbotHistory(ChatbotHistory chatbotHistory) {
        return chatbotHistoryRepository.save(chatbotHistory);
    }
    
    public ChatbotHistory updateChatbotHistory(String id, ChatbotHistory chatbotHistoryDetails) {
        ChatbotHistory chatbotHistory = chatbotHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ChatbotHistory not found"));
        
        chatbotHistory.setSessionId(chatbotHistoryDetails.getSessionId());
        chatbotHistory.setMessage(chatbotHistoryDetails.getMessage());
        chatbotHistory.setIsUserMessage(chatbotHistoryDetails.getIsUserMessage());
        chatbotHistory.setTimestamp(chatbotHistoryDetails.getTimestamp());
        
        return chatbotHistoryRepository.save(chatbotHistory);
    }
    
    public void deleteChatbotHistory(String id) {
        chatbotHistoryRepository.deleteById(id);
    }
}
