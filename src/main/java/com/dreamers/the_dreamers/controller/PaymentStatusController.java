package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.PaymentStatus;
import com.dreamers.the_dreamers.service.PaymentStatusService;
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
@RequestMapping("/api/payment-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Payment Status API", description = "API for managing payment statuses")
public class PaymentStatusController {

    private final PaymentStatusService paymentStatusService;

    @Operation(summary = "Get all payment statuses",
            description = "Returns a list of all defined payment statuses.")
    @ApiResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PaymentStatus.class)))
    @GetMapping
    public ResponseEntity<List<PaymentStatus>> getAllPaymentStatuses() {
        return ResponseEntity.ok(paymentStatusService.getAllPaymentStatuses());
    }

    @Operation(summary = "Get payment status by ID",
            description = "Returns a single payment status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PaymentStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatus> getPaymentStatusById(
            @Parameter(description = "ID of the payment status to retrieve")
            @PathVariable String id) {
        return paymentStatusService.getPaymentStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get payment status by name",
            description = "Returns a single payment status based on its name.")
    @ApiResponse(responseCode = "200", description = "Status found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PaymentStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found")
    @GetMapping("/name/{statusName}")
    public ResponseEntity<PaymentStatus> getPaymentStatusByStatusName(
            @Parameter(description = "Name of the payment status", example = "PAID")
            @PathVariable String statusName) {
        return paymentStatusService.getPaymentStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new payment status",
            description = "Creates a new payment status.")
    @ApiResponse(responseCode = "200", description = "Status created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PaymentStatus.class)))
    @PostMapping
    public ResponseEntity<PaymentStatus> createPaymentStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Details of the new payment status", required = true)
            @RequestBody PaymentStatus paymentStatus) {
        return ResponseEntity.ok(paymentStatusService.createPaymentStatus(paymentStatus));
    }

    @Operation(summary = "Update a payment status",
            description = "Updates an existing payment status based on its ID.")
    @ApiResponse(responseCode = "200", description = "Status updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PaymentStatus.class)))
    @ApiResponse(responseCode = "404", description = "Status not found for update")
    @PutMapping("/{id}")
    public ResponseEntity<PaymentStatus> updatePaymentStatus(
            @Parameter(description = "ID of the payment status to update")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Updated payment status details", required = true)
            @RequestBody PaymentStatus paymentStatusDetails) {
        try {
            PaymentStatus updatedPaymentStatus = paymentStatusService.updatePaymentStatus(id, paymentStatusDetails);
            return ResponseEntity.ok(updatedPaymentStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a payment status",
            description = "Deletes a payment status based on its ID.")
    @ApiResponse(responseCode = "204", description = "Status deleted successfully, no content returned")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentStatus(
            @Parameter(description = "ID of the payment status to delete")
            @PathVariable String id) {
        paymentStatusService.deletePaymentStatus(id);
        return ResponseEntity.noContent().build();
    }
}