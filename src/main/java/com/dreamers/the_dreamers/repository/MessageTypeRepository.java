package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageTypeRepository extends JpaRepository<MessageType, String> {
    Optional<MessageType> findByTypeName(String typeName);
}
