package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, String> {
    Optional<ProjectStatus> findByStatusName(String statusName);
}
