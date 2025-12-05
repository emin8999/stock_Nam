package com.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.Payment;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
   List<Payment> findAllByArId(String arId);
   List<Payment> findAllByApId(String apId);

}
