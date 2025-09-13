package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findByConversationIdOrderBySentAtAsc(Long conversationId);
    Page<Message> findByConversationIdOrderBySentAtDesc(Long conversationId, Pageable pageable);
    List<Message> findBySenderId(Long senderId);
}
