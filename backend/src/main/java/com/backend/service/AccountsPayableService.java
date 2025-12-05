package com.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.dto.PaymentDTO;
import com.backend.entity.AccountsPayable;
import com.backend.entity.Payment;
import com.backend.repository.AccountsPayableRepository;
import com.backend.repository.PaymentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountsPayableService {
    
    private final AccountsPayableRepository apRepository;
    private final PaymentRepository paymentRepository;
    
    public List<AccountsPayable> findAll() {
        return apRepository.findAll();
    }
    
    public AccountsPayable findById(String id) {
        return apRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AP not found: " + id));
    }
    
    @Transactional
    public AccountsPayable create(AccountsPayable ap) {
        return apRepository.save(ap);
    }
    
    @Transactional
    public AccountsPayable addPayment(String id, Payment payment) {
        AccountsPayable ap = findById(id);
        ap.getPayments().add(payment);
        
        BigDecimal totalPaid = ap.getPayments().stream()
            .map(Payment::getAmountAzn)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal remaining = ap.getAmountAzn().subtract(totalPaid);
        
        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            ap.setStatus(AccountsPayable.Status.closed);
        } else {
            ap.setStatus(AccountsPayable.Status.partial);
        }
        
        return apRepository.save(ap);
    }
    
    @Transactional
    public void delete(String id) {
        apRepository.deleteById(id);
    }

    public PaymentDTO getPaymentById (String id){
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found: " + id));
        PaymentDTO dto = new PaymentDTO();
        dto.setAmountAzn(payment.getAmountAzn());
        dto.setNote(payment.getNote());

        return dto;
    }
}
