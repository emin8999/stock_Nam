package com.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.entity.AccountsPayable;
import com.backend.entity.Payment;
import com.backend.repository.AccountsPayableRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountsPayableService {
    
    private final AccountsPayableRepository apRepository;
    
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
}
