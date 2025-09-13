package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.ApplicationStatus;
import com.dreamers.the_dreamers.repository.ApplicationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationStatusService {
    
    private final ApplicationStatusRepository applicationStatusRepository;
    
    public List<ApplicationStatus> getAllApplicationStatuses() {
        return applicationStatusRepository.findAll();
    }
    
    public Optional<ApplicationStatus> getApplicationStatusById(String id) {
        return applicationStatusRepository.findById(id);
    }
    
    public Optional<ApplicationStatus> getApplicationStatusByStatusName(String statusName) {
        return applicationStatusRepository.findByStatusName(statusName);
    }
    
    public ApplicationStatus createApplicationStatus(ApplicationStatus applicationStatus) {
        return applicationStatusRepository.save(applicationStatus);
    }
    
    public ApplicationStatus updateApplicationStatus(String id, ApplicationStatus applicationStatusDetails) {
        ApplicationStatus applicationStatus = applicationStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ApplicationStatus not found"));
        
        applicationStatus.setStatusName(applicationStatusDetails.getStatusName());
        
        return applicationStatusRepository.save(applicationStatus);
    }
    
    public void deleteApplicationStatus(String id) {
        applicationStatusRepository.deleteById(id);
    }
}
