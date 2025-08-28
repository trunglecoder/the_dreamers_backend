package com.dreamers.the_dreamers.controller;

import com.dreamers.the_dreamers.model.PaymentStatus;
import com.dreamers.the_dreamers.service.PaymentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-statuses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentStatusController {
    
    private final PaymentStatusService paymentStatusService;
    
    @GetMapping
    public ResponseEntity<List<PaymentStatus>> getAllPaymentStatuses() {
        return ResponseEntity.ok(paymentStatusService.getAllPaymentStatuses());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatus> getPaymentStatusById(@PathVariable String id) {
        return paymentStatusService.getPaymentStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{statusName}")
    public ResponseEntity<PaymentStatus> getPaymentStatusByStatusName(@PathVariable String statusName) {
        return paymentStatusService.getPaymentStatusByStatusName(statusName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<PaymentStatus> createPaymentStatus(@RequestBody PaymentStatus paymentStatus) {
        return ResponseEntity.ok(paymentStatusService.createPaymentStatus(paymentStatus));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PaymentStatus> updatePaymentStatus(@PathVariable String id, @RequestBody PaymentStatus paymentStatusDetails) {
        try {
            PaymentStatus updatedPaymentStatus = paymentStatusService.updatePaymentStatus(id, paymentStatusDetails);
            return ResponseEntity.ok(updatedPaymentStatus);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable String id) {
        paymentStatusService.deletePaymentStatus(id);
        return ResponseEntity.noContent().build();
    }
}
