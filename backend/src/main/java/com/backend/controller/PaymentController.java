package com.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
public class PaymentController {

        private final PaymentService paymentService;

        
    @GetMapping("/creditor/{arId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByCreditor(@PathVariable String arId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByCreditor(arId);
        return ResponseEntity.ok(payments);
    }
}
