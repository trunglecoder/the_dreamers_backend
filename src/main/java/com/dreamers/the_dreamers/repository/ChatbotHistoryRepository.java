package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.ChatbotHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatbotHistoryRepository extends JpaRepository<ChatbotHistory, String> {
    List<ChatbotHistory> findByUserId(Long userId);
    List<ChatbotHistory> findBySessionId(String sessionId);
}
