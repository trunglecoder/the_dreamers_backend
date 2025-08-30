package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Application;
import com.dreamers.the_dreamers.model.ApplicationStatus;
import com.dreamers.the_dreamers.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }
    
    public Page<Application> getAllApplications(Pageable pageable) {
        return applicationRepository.findAll(pageable);
    }
    
    public Optional<Application> getApplicationById(String id) {
        return applicationRepository.findById(id);
    }
    
    public List<Application> getApplicationsByUserId(String userId) {
        return applicationRepository.findByUserId(userId);
    }
    
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }
    
    public Page<Application> getApplicationsByStatus(ApplicationStatus status, Pageable pageable) {
        return applicationRepository.findByStatus(status, pageable);
    }
    
    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }
    
    public Application updateApplication(String id, Application applicationDetails) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        application.setApplicationDate(applicationDetails.getApplicationDate());
        application.setStatus(applicationDetails.getStatus());
        application.setResumeUrl(applicationDetails.getResumeUrl());
        application.setCoverLetterUrl(applicationDetails.getCoverLetterUrl());
        application.setResumeSummary(applicationDetails.getResumeSummary());
        application.setNotes(applicationDetails.getNotes());
        application.setInterviewCall(applicationDetails.getInterviewCall());
        
        return applicationRepository.save(application);
    }
    
    public void deleteApplication(String id) {
        applicationRepository.deleteById(id);
    }
}
