package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.ReportType;
import com.dreamers.the_dreamers.repository.ReportTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportTypeService {
    
    private final ReportTypeRepository reportTypeRepository;
    
    public List<ReportType> getAllReportTypes() {
        return reportTypeRepository.findAll();
    }
    
    public Optional<ReportType> getReportTypeById(String id) {
        return reportTypeRepository.findById(id);
    }
    
    public Optional<ReportType> getReportTypeByTypeName(String typeName) {
        return reportTypeRepository.findByTypeName(typeName);
    }
    
    public ReportType createReportType(ReportType reportType) {
        return reportTypeRepository.save(reportType);
    }
    
    public ReportType updateReportType(String id, ReportType reportTypeDetails) {
        ReportType reportType = reportTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ReportType not found"));
        
        reportType.setTypeName(reportTypeDetails.getTypeName());
        
        return reportTypeRepository.save(reportType);
    }
    
    public void deleteReportType(String id) {
        reportTypeRepository.deleteById(id);
    }
}
