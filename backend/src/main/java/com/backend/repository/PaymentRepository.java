package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.Payment;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
   // List<Payment> findByReferenceIdAndReferenceType(Long referenceId, Payment.ReferenceType type);
}
