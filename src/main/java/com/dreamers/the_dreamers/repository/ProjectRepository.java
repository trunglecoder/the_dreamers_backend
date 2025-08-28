package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.Project;
import com.dreamers.the_dreamers.model.ProjectStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    List<Project> findByStatus(ProjectStatus status);
    Page<Project> findByStatus(ProjectStatus status, Pageable pageable);
    List<Project> findByCollectedAmountGreaterThanEqual(double amount);
}
