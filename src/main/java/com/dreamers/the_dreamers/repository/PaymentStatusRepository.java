package com.dreamers.the_dreamers.repository;

import com.dreamers.the_dreamers.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, String> {
    Optional<PaymentStatus> findByStatusName(String statusName);
}
