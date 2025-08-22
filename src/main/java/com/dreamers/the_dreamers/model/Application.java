package com.dreamers.the_dreamers.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@ToString(callSuper = true)
public class Application extends BaseEntity {
    
    @Column(name = "application_date")
    private LocalDateTime applicationDate;
    
    @Column(name = "resume_url", length = 255)
    private String resumeUrl;
    
    @Column(name = "cover_letter_url", length = 255)
    private String coverLetterUrl;
    
    @Column(name = "resume_summary", columnDefinition = "TEXT")
    private String resumeSummary;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-applications")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ApplicationStatus status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_call_id")
    @JsonBackReference("videoCall-applications")
    private VideoCall interviewCall;
}
