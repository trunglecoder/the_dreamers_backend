package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.ProjectStatus;
import com.dreamers.the_dreamers.repository.ProjectStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectStatusService {
    
    private final ProjectStatusRepository projectStatusRepository;
    
    public List<ProjectStatus> getAllProjectStatuses() {
        return projectStatusRepository.findAll();
    }
    
    public Optional<ProjectStatus> getProjectStatusById(String id) {
        return projectStatusRepository.findById(id);
    }
    
    public Optional<ProjectStatus> getProjectStatusByStatusName(String statusName) {
        return projectStatusRepository.findByStatusName(statusName);
    }
    
    public ProjectStatus createProjectStatus(ProjectStatus projectStatus) {
        return projectStatusRepository.save(projectStatus);
    }
    
    public ProjectStatus updateProjectStatus(String id, ProjectStatus projectStatusDetails) {
        ProjectStatus projectStatus = projectStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProjectStatus not found"));
        
        projectStatus.setStatusName(projectStatusDetails.getStatusName());
        
        return projectStatusRepository.save(projectStatus);
    }
    
    public void deleteProjectStatus(String id) {
        projectStatusRepository.deleteById(id);
    }
}
