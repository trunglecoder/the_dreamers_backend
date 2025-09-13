package com.dreamers.the_dreamers.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@ToString
public class UserDetails {
    
    @Id
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "first_name", length = 255)
    private String firstName;
    
    @Column(name = "last_name", length = 255)
    private String lastName;
    
    @Column(name = "dob")
    private LocalDate dob;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "address", length = 255)
    private String address;
    
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;
    
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;
    
    @Column(name = "occupation", length = 255)
    private String occupation;
    
    @Column(name = "donation_count", nullable = false)
    private Integer donationCount = 0;
    
    // Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-userDetails")
    private User user;
    
    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("userDetails-socialMedia")
    private List<SocialMedia> socialMedia = new ArrayList<>();
}
