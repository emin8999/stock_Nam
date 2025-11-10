package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entity.AccountsReceivable;
import com.backend.entity.Payment;
import com.backend.repository.AccountsReceivableRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountReceivableService {
    
    private final AccountsReceivableRepository arRepository;
    
    public List<AccountsReceivable> findAll() {
        return arRepository.findAll();
    }
    
    public AccountsReceivable findById(String id) {
        return arRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("AR not found: " + id));
    }
    
    @Transactional
    public AccountsReceivable create(AccountsReceivable ar) {
        return arRepository.save(ar);
    }
    
    @Transactional
    public AccountsReceivable addPayment(String id, Payment payment) {
        AccountsReceivable ar = findById(id);
        ar.getPayments().add(payment);
        
        BigDecimal totalPaid = ar.getPayments().stream()
            .map(Payment::getAmountAzn)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal remaining = ar.getAmountAzn().subtract(totalPaid);
        
        if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
            ar.setStatus(AccountsReceivable.Status.closed);
        } else {
            ar.setStatus(AccountsReceivable.Status.partial);
        }
        
        return arRepository.save(ar);
    }
    
    @Transactional
    public void delete(String id) {
        arRepository.deleteById(id);
    }
}
