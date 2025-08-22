package com.dreamers.the_dreamers.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message_types")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class MessageType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "type_name", nullable = false, unique = true, length = 50)
    private String typeName;
}
