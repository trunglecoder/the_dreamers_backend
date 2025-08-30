package com.dreamers.the_dreamers.service;

import com.dreamers.the_dreamers.model.Payment;
import com.dreamers.the_dreamers.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    public Optional<Payment> getPaymentById(String id) {
        return paymentRepository.findById(id);
    }
    
    public List<Payment> getPaymentsByDonationId(String donationId) {
        return paymentRepository.findByDonationId(donationId);
    }
    
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    
    public Payment updatePayment(String id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        payment.setAmount(paymentDetails.getAmount());
        payment.setStatus(paymentDetails.getStatus());
        payment.setPaymentMethod(paymentDetails.getPaymentMethod());
        payment.setPaymentLink(paymentDetails.getPaymentLink());
        payment.setPaidAt(paymentDetails.getPaidAt());
        
        return paymentRepository.save(payment);
    }
    
    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }
}
