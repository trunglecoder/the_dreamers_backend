package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.ReportStatus;
import com.dreamers.the_dreamers.repository.ReportStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportStatusService {
    
    private final ReportStatusRepository reportStatusRepository;
    
    public List<ReportStatus> getAllReportStatuses() {
        return reportStatusRepository.findAll();
    }
    
    public Optional<ReportStatus> getReportStatusById(String id) {
        return reportStatusRepository.findById(id);
    }
    
    public Optional<ReportStatus> getReportStatusByStatusName(String statusName) {
        return reportStatusRepository.findByStatusName(statusName);
    }
    
    public ReportStatus createReportStatus(ReportStatus reportStatus) {
        return reportStatusRepository.save(reportStatus);
    }
    
    public ReportStatus updateReportStatus(String id, ReportStatus reportStatusDetails) {
        ReportStatus reportStatus = reportStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ReportStatus not found"));
        
        reportStatus.setStatusName(reportStatusDetails.getStatusName());
        
        return reportStatusRepository.save(reportStatus);
    }
    
    public void deleteReportStatus(String id) {
        reportStatusRepository.deleteById(id);
    }
}
