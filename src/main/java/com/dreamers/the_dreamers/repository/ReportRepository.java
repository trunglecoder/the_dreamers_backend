package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.Report;
import com.dreamers.the_dreamers.model.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    List<Report> findByReporterId(Long reporterId);
    List<Report> findByStatus(ReportStatus status);
    Page<Report> findByStatus(ReportStatus status, Pageable pageable);
}
