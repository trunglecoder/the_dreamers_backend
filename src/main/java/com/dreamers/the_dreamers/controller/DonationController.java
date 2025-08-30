package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Donation;
import com.dreamers.the_dreamers.service.DonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Donation API", description = "API for managing donations")
public class DonationController {

    private final DonationService donationService;

    @Operation(summary = "Get all donations",
            description = "Returns a list of all donations.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Donation.class)))
    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @Operation(summary = "Get donations with pagination",
            description = "Returns a paginated list of donations.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/page")
    public ResponseEntity<Page<Donation>> getAllDonationsPaginated(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of donations per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(donationService.getAllDonations(pageable));
    }

    @Operation(summary = "Get donation by ID",
            description = "Returns a single donation based on its ID.")
    @ApiResponse(responseCode = "200", description = "Donation found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Donation.class)))
    @ApiResponse(responseCode = "404", description = "Donation not found")
    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(
            @Parameter(description = "ID of the donation to retrieve")
            @PathVariable String id) {
        return donationService.getDonationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get donations for a specific project",
            description = "Returns a list of all donations for a given project ID.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Donation.class)))
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Donation>> getDonationsByProject(
            @Parameter(description = "ID of the project")
            @PathVariable String projectId) {
        return ResponseEntity.ok(donationService.getDonationsByProjectId(projectId));
    }

    @Operation(summary = "Get paginated donations for a specific project",
            description = "Returns a paginated list of donations for a given project ID.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Page.class)))
    @GetMapping("/project/{projectId}/page")
    public ResponseEntity<Page<Donation>> getDonationsByProjectPaginated(
            @Parameter(description = "ID of the project")
            @PathVariable String projectId,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of donations per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(donationService.getDonationsByProjectId(projectId, pageable));
    }

    @Operation(summary = "Get donations by donor email",
            description = "Returns a list of donations made by a specific donor email.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Donation.class)))
    @GetMapping("/donor/{email}")
    public ResponseEntity<List<Donation>> getDonationsByDonorEmail(
            @Parameter(description = "Email of the donor")
            @PathVariable String email) {
        return ResponseEntity.ok(donationService.getDonationsByDonorEmail(email));
    }

    @Operation(summary = "Create a new donation",
            description = "Creates a new donation record.")
    @ApiResponse(responseCode = "200", description = "Donation created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Donation.class)))
    @PostMapping
    public ResponseEntity<Donation> createDonation(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new donation", required = true)
            @RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.createDonation(donation));
    }

    @Operation(summary = "Update a donation",
            description = "Updates an existing donation record based on its ID.")
    @ApiResponse(responseCode = "200", description = "Donation updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Donation.class)))
    @ApiResponse(responseCode = "404", description = "Donation not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(
            @Parameter(description = "ID of the donation to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated donation details", required = true)
            @RequestBody Donation donationDetails) {
        try {
            Donation updatedDonation = donationService.updateDonation(id, donationDetails);
            return ResponseEntity.ok(updatedDonation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a donation",
            description = "Deletes a donation record based on its ID.")
    @ApiResponse(responseCode = "204", description = "Donation deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(
            @Parameter(description = "ID of the donation to delete")
            @PathVariable String id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }
}