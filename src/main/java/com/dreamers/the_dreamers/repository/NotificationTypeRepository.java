package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType, String> {
    Optional<NotificationType> findByTypeName(String typeName);
}
