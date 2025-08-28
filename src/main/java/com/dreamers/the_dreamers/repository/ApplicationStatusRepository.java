package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationStatusRepository extends JpaRepository<ApplicationStatus, String> {
    Optional<ApplicationStatus> findByStatusName(String statusName);
}
