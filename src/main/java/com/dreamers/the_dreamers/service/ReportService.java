package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Report;
import com.dreamers.the_dreamers.model.ReportStatus;
import com.dreamers.the_dreamers.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    
    private final ReportRepository reportRepository;
    
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
    
    public Page<Report> getAllReports(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }
    
    public Optional<Report> getReportById(String id) {
        return reportRepository.findById(id);
    }
    
    public List<Report> getReportsByReporterId(Long reporterId) {
        return reportRepository.findByReporterId(reporterId);
    }
    
    public List<Report> getReportsByStatus(ReportStatus status) {
        return reportRepository.findByStatus(status);
    }
    
    public Page<Report> getReportsByStatus(ReportStatus status, Pageable pageable) {
        return reportRepository.findByStatus(status, pageable);
    }
    
    public Report createReport(Report report) {
        return reportRepository.save(report);
    }
    
    public Report updateReport(String id, Report reportDetails) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        
        report.setReportedUrl(reportDetails.getReportedUrl());
        report.setDescription(reportDetails.getDescription());
        report.setStatus(reportDetails.getStatus());
        report.setReportType(reportDetails.getReportType());
        
        return reportRepository.save(report);
    }
    
    public void deleteReport(String id) {
        reportRepository.deleteById(id);
    }
}
