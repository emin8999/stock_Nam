package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entity.StoreStock;
import com.backend.entity.StoreTransfer;
import com.backend.entity.WarehouseStock;
import com.backend.repository.StoreStockRepository;
import com.backend.repository.StoreTransferRepository;
import com.backend.repository.WarehouseStockRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreTransferService {
    
    private final StoreTransferRepository storeTransferRepository;
    private final WarehouseStockRepository warehouseStockRepository;
    private final StoreStockRepository storeStockRepository;
    
    public List<StoreTransfer> findAll() {
        return storeTransferRepository.findAll();
    }
    
    public List<StoreTransfer> findByDateRange(LocalDate from, LocalDate to) {
        return storeTransferRepository.findByDateBetween(from, to);
    }
    
    @Transactional
    public StoreTransfer create(StoreTransfer transfer) {
       
        WarehouseStock warehouseStock = warehouseStockRepository.findByProductId(transfer.getProductId())
            .orElseThrow(() -> new RuntimeException("Məhsul anbarda yoxdur"));
        
        if (warehouseStock.getQuantity().compareTo(transfer.getQuantity()) < 0) {
            throw new RuntimeException("Anbarda kifayət qədər məhsul yoxdur. Anbarda: " + warehouseStock.getQuantity());
        }
        
        warehouseStock.setQuantity(warehouseStock.getQuantity().subtract(transfer.getQuantity()));
        warehouseStockRepository.save(warehouseStock);
        
        
        StoreStock storeStock = storeStockRepository.findByProductId(transfer.getProductId())
            .orElse(new StoreStock(null, transfer.getProductId(), BigDecimal.ZERO));
        
        storeStock.setQuantity(storeStock.getQuantity().add(transfer.getQuantity()));
        storeStockRepository.save(storeStock);
        
        return storeTransferRepository.save(transfer);
    }
    
    @Transactional
    public void delete(String id) {
        storeTransferRepository.deleteById(id);
    }
}
