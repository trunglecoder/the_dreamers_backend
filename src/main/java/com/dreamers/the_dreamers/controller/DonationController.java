package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Donation;
import com.dreamers.the_dreamers.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DonationController {
    
    private final DonationService donationService;
    
    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }
    
    @GetMapping("/page")
    public ResponseEntity<Page<Donation>> getAllDonationsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(donationService.getAllDonations(pageable));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable String id) {
        return donationService.getDonationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Donation>> getDonationsByProject(@PathVariable String projectId) {
        return ResponseEntity.ok(donationService.getDonationsByProjectId(projectId));
    }
    
    @GetMapping("/project/{projectId}/page")
    public ResponseEntity<Page<Donation>> getDonationsByProjectPaginated(
            @PathVariable String projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(donationService.getDonationsByProjectId(projectId, pageable));
    }
    
    @GetMapping("/donor/{email}")
    public ResponseEntity<List<Donation>> getDonationsByDonorEmail(@PathVariable String email) {
        return ResponseEntity.ok(donationService.getDonationsByDonorEmail(email));
    }
    
    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.createDonation(donation));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(@PathVariable String id, @RequestBody Donation donationDetails) {
        try {
            Donation updatedDonation = donationService.updateDonation(id, donationDetails);
            return ResponseEntity.ok(updatedDonation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable String id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }
}
