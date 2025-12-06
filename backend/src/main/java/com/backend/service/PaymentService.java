package com.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.dto.PaymentDTO;
import com.backend.entity.Payment;
import com.backend.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

   

    public PaymentDTO getPaymentById(String id) {

        var payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment tapılmadı: " + id));

      
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setDate(payment.getDate().toString());
        dto.setAmountAzn(payment.getAmountAzn());
        dto.setNote(payment.getNote());
        dto.setArId(payment.getArId());
        dto.setApId(payment.getApId());

        return dto;
    }

       
    public List<PaymentDTO> getPaymentsByCreditor(String apId) {
        List<Payment> payments = paymentRepository.findAllByArId(apId);

        return payments.stream().map(payment -> {
            PaymentDTO dto = new PaymentDTO();
            dto.setId(payment.getId());
            dto.setDate(payment.getDate().toString()); 
            dto.setAmountAzn(payment.getAmountAzn());
            dto.setNote(payment.getNote());
            dto.setArId(payment.getArId());
            dto.setApId(payment.getApId());
            return dto;
        }).toList();
    }
}
