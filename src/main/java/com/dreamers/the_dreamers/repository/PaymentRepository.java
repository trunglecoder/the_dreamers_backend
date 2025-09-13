package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByDonationId(Long donationId);
}
