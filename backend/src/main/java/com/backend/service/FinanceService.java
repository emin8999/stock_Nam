package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entity.Finance;
import com.backend.repository.FinanceRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanceService {
    
    private final FinanceRepository financeRepository;
    
    public List<Finance> findAll() {
        return financeRepository.findAll();
    }
    
    public List<Finance> findByDateRange(LocalDate from, LocalDate to) {
        return financeRepository.findByDateBetween(from, to);
    }
    
    public Finance findById(String id) {
        return financeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Finance record not found: " + id));
    }
    
    @Transactional
    public Finance create(Finance finance) {
        return financeRepository.save(finance);
    }
    
    @Transactional
    public void delete(String id) {
        financeRepository.deleteById(id);
    }
}

