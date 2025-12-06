package com.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.PaymentDTO;
import com.backend.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    
    @GetMapping("/debtor/{arId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByArId(@PathVariable String arId) {
        return ResponseEntity.ok(paymentService.getPaymentsByArId(arId));
    }

  
    @GetMapping("/creditor/{apId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByApId(@PathVariable String apId) {
        return ResponseEntity.ok(paymentService.getPaymentsByApId(apId));
    }

    
    @GetMapping
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }
}
