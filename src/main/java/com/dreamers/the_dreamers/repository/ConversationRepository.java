package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {
    List<Conversation> findByParticipant1IdOrParticipant2Id(Long participant1Id, Long participant2Id);
}
