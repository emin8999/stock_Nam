package com.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.entity.Receipt;
import com.backend.repository.ReceiptRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    
    private final ReceiptRepository receiptRepository;
    
    public List<Receipt> findAll() {
        return receiptRepository.findAll();
    }
    
    public List<Receipt> findByMaterial(String materialId) {
        return receiptRepository.findByMaterialId(materialId);
    }
    
    public List<Receipt> findByDateRange(LocalDate from, LocalDate to) {
        return receiptRepository.findByDateBetween(from, to);
    }
    
    public Receipt findById(String id) {
        return receiptRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Receipt not found: " + id));
    }
    
    @Transactional
    public Receipt create(Receipt receipt) {
        receipt.setTotalCost(receipt.getQtyKg().multiply(receipt.getPricePerKg()));
        return receiptRepository.save(receipt);
    }
    
    @Transactional
    public void delete(String id) {
        receiptRepository.deleteById(id);
    }
}
