package com.dreamers.the_dreamers.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "report_types")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class ReportType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "type_name", nullable = false, unique = true, length = 50)
    private String typeName;
}
