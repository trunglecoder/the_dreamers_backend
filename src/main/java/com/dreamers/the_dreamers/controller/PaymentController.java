package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.Payment;
import com.dreamers.the_dreamers.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Payment API", description = "API for managing payment transactions")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Get all payments",
            description = "Returns a list of all payment transactions.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Payment.class)))
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @Operation(summary = "Get payment by ID",
            description = "Returns a single payment transaction based on its ID.")
    @ApiResponse(responseCode = "200", description = "Payment found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Payment.class)))
    @ApiResponse(responseCode = "404", description = "Payment not found")
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @Parameter(description = "ID of the payment to retrieve")
            @PathVariable String id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get payments by donation ID",
            description = "Returns a list of payments for a specific donation.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Payment.class)))
    @GetMapping("/donation/{donationId}")
    public ResponseEntity<List<Payment>> getPaymentsByDonation(
            @Parameter(description = "ID of the donation")
            @PathVariable String donationId) {
        return ResponseEntity.ok(paymentService.getPaymentsByDonationId(donationId));
    }

    @Operation(summary = "Create a new payment",
            description = "Creates a new payment transaction record.")
    @ApiResponse(responseCode = "200", description = "Payment created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Payment.class)))
    @PostMapping
    public ResponseEntity<Payment> createPayment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new payment", required = true)
            @RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.createPayment(payment));
    }

    @Operation(summary = "Update a payment",
            description = "Updates an existing payment transaction based on its ID.")
    @ApiResponse(responseCode = "200", description = "Payment updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Payment.class)))
    @ApiResponse(responseCode = "404", description = "Payment not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @Parameter(description = "ID of the payment to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated payment details", required = true)
            @RequestBody Payment paymentDetails) {
        try {
            Payment updatedPayment = paymentService.updatePayment(id, paymentDetails);
            return ResponseEntity.ok(updatedPayment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a payment",
            description = "Deletes a payment transaction based on its ID.")
    @ApiResponse(responseCode = "204", description = "Payment deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @Parameter(description = "ID of the payment to delete")
            @PathVariable String id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}