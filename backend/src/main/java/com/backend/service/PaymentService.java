package com.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.dto.PaymentDTO;
import com.backend.entity.Payment;
import com.backend.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

   

    // public PaymentDTO getPaymentById(String id) {

    //     var payment = paymentRepository.findById(id)
    //             .orElseThrow(() -> new RuntimeException("Payment tapılmadı: " + id));

      
    //     PaymentDTO dto = new PaymentDTO();
    //     dto.setId(payment.getId());
    //     dto.setDate(payment.getDate().toString());
    //     dto.setAmountAzn(payment.getAmountAzn());
    //     dto.setNote(payment.getNote());
    //     dto.setArId(payment.getArId());
    //     dto.setApId(payment.getApId());

    //     return dto;
    // }

       
    // public List<PaymentDTO> getPaymentsByCreditor(String apId) {
    //     List<Payment> payments = paymentRepository.findAllByApId(apId);

    //     return payments.stream().map(payment -> {
    //         PaymentDTO dto = new PaymentDTO();
    //         dto.setId(payment.getId());
    //         dto.setDate(payment.getDate().toString()); 
    //         dto.setAmountAzn(payment.getAmountAzn());
    //         dto.setNote(payment.getNote());
    //         dto.setArId(payment.getArId());
    //         dto.setApId(payment.getApId());
    //         return dto;
    //     }).toList();
    // }


    

    public List<PaymentDTO> getPaymentsByArId(String arId) {
        return paymentRepository.findByArIdOrderByDateDesc(arId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentDTO> getPaymentsByApId(String apId) {
        return paymentRepository.findByApIdOrderByDateDesc(apId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAllByOrderByDateDesc()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private PaymentDTO toDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .amountAzn(payment.getAmountAzn())
                .date(payment.getDate().toString())
                .note(payment.getNote())
                .arId(payment.getArId())
                .apId(payment.getApId())
                .build();
    }
}
