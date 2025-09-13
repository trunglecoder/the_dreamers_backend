package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportStatusRepository extends JpaRepository<ReportStatus, String> {
    Optional<ReportStatus> findByStatusName(String statusName);
}
