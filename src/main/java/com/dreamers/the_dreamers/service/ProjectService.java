package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Project;
import com.dreamers.the_dreamers.model.ProjectStatus;
import com.dreamers.the_dreamers.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }
    
    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }
    
    public List<Project> getProjectsByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }
    
    public Page<Project> getProjectsByStatus(ProjectStatus status, Pageable pageable) {
        return projectRepository.findByStatus(status, pageable);
    }
    
    public List<Project> getProjectsByCollectedAmount(double amount) {
        return projectRepository.findByCollectedAmountGreaterThanEqual(amount);
    }
    
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }
    
    public Project updateProject(String id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        project.setStartDate(projectDetails.getStartDate());
        project.setEndDate(projectDetails.getEndDate());
        project.setTargetAmount(projectDetails.getTargetAmount());
        project.setCollectedAmount(projectDetails.getCollectedAmount());
        project.setStatus(projectDetails.getStatus());
        
        return projectRepository.save(project);
    }
    
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}
