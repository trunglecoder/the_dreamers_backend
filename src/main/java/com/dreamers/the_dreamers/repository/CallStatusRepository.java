package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.CallStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CallStatusRepository extends JpaRepository<CallStatus, String> {
    Optional<CallStatus> findByStatusName(String statusName);
}
