package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, String> {
    List<Donation> findByProjectId(String projectId);
    Page<Donation> findByProjectId(String projectId, Pageable pageable);
    List<Donation> findByEmail(String donorEmail);
}
