package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.PaymentStatus;
import com.dreamers.the_dreamers.repository.PaymentStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentStatusService {
    
    private final PaymentStatusRepository paymentStatusRepository;
    
    public List<PaymentStatus> getAllPaymentStatuses() {
        return paymentStatusRepository.findAll();
    }
    
    public Optional<PaymentStatus> getPaymentStatusById(String id) {
        return paymentStatusRepository.findById(id);
    }
    
    public Optional<PaymentStatus> getPaymentStatusByStatusName(String statusName) {
        return paymentStatusRepository.findByStatusName(statusName);
    }
    
    public PaymentStatus createPaymentStatus(PaymentStatus paymentStatus) {
        return paymentStatusRepository.save(paymentStatus);
    }
    
    public PaymentStatus updatePaymentStatus(String id, PaymentStatus paymentStatusDetails) {
        PaymentStatus paymentStatus = paymentStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PaymentStatus not found"));
        
        paymentStatus.setStatusName(paymentStatusDetails.getStatusName());
        
        return paymentStatusRepository.save(paymentStatus);
    }
    
    public void deletePaymentStatus(String id) {
        paymentStatusRepository.deleteById(id);
    }
}
