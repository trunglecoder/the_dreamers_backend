package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Donation;
import com.dreamers.the_dreamers.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonationService {
    
    private final DonationRepository donationRepository;
    
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }
    
    public Page<Donation> getAllDonations(Pageable pageable) {
        return donationRepository.findAll(pageable);
    }
    
    public Optional<Donation> getDonationById(String id) {
        return donationRepository.findById(id);
    }
    
    public List<Donation> getDonationsByProjectId(String projectId) {
        return donationRepository.findByProjectId(projectId);
    }
    
    public Page<Donation> getDonationsByProjectId(String projectId, Pageable pageable) {
        return donationRepository.findByProjectId(projectId, pageable);
    }
    
    public List<Donation> getDonationsByDonorEmail(String donorEmail) {
        return donationRepository.findByEmail(donorEmail);
    }
    
    public Donation createDonation(Donation donation) {
        return donationRepository.save(donation);
    }
    
    public Donation updateDonation(String id, Donation donationDetails) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
        
        donation.setDonorName(donationDetails.getDonorName());
        donation.setEmail(donationDetails.getEmail());
        donation.setAmount(donationDetails.getAmount());
        donation.setDonationDate(donationDetails.getDonationDate());
        donation.setProject(donationDetails.getProject());
        
        return donationRepository.save(donation);
    }
    
    public void deleteDonation(String id) {
        donationRepository.deleteById(id);
    }
}
