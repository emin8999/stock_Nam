package com.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.dto.KpiDTO;
import com.backend.dto.PaymentDTO;
import com.backend.dto.ProfitLossDTO;
import com.backend.dto.StockDTO;
import com.backend.entity.AccountsPayable;
import com.backend.entity.AccountsReceivable;
import com.backend.entity.Finance;
import com.backend.entity.Issue;
import com.backend.entity.Material;
import com.backend.entity.Payment;
import com.backend.entity.Receipt;
import com.backend.entity.Settings;
import com.backend.repository.AccountsPayableRepository;
import com.backend.repository.AccountsReceivableRepository;
import com.backend.repository.FinanceRepository;
import com.backend.repository.IssueRepository;
import com.backend.repository.MaterialRepository;
import com.backend.repository.PaymentRepository;
import com.backend.repository.ReceiptRepository;
import com.backend.repository.SettingsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialService {
    
    private final MaterialRepository materialRepository;
    
    public List<Material> findAll() {
        return materialRepository.findAll();
    }
    
    public List<Material> findActive() {
        return materialRepository.findByIsActiveTrue();
    }
    
    public Material findById(String id) {
        return materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Material not found: " + id));
    }
    
    @Transactional
    public Material create(Material material) {
        if (materialRepository.existsByCode(material.getCode())) {
            throw new RuntimeException("Material code already exists: " + material.getCode());
        }
        return materialRepository.save(material);
    }
    
    @Transactional
    public Material update(String id, Material material) {
        Material existing = findById(id);
        existing.setName(material.getName());
        existing.setMinLevel(material.getMinLevel());
        existing.setIsActive(material.getIsActive());
        return materialRepository.save(existing);
    }
    
    @Transactional
    public void toggleActive(String id) {
        Material material = findById(id);
        material.setIsActive(!material.getIsActive());
        materialRepository.save(material);
    }
    
    @Transactional
    public void delete(String id) {
        materialRepository.deleteById(id);
    }
}
