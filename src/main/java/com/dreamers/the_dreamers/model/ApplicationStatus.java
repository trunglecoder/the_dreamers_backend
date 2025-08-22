package com.dreamers.the_dreamers.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "application_status")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class ApplicationStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "status_name", nullable = false, unique = true, length = 50)
    private String statusName;
}
