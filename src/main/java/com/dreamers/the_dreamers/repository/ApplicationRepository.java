package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.Application;
import com.dreamers.the_dreamers.model.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {
    List<Application> findByUserId(String userId);
    List<Application> findByStatus(ApplicationStatus status);
    Page<Application> findByStatus(ApplicationStatus status, Pageable pageable);
}
