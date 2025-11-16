package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entity.StoreSale;
import com.backend.entity.StoreStock;
import com.backend.repository.StoreSaleRepository;
import com.backend.repository.StoreStockRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreSaleService {
    
    private final StoreSaleRepository storeSaleRepository;
    private final StoreStockRepository storeStockRepository;
    
    public List<StoreSale> findAll() {
        return storeSaleRepository.findAll();
    }
    
    public List<StoreSale> findByDateRange(LocalDate from, LocalDate to) {
        return storeSaleRepository.findByDateBetween(from, to);
    }
    
    @Transactional
    public StoreSale create(StoreSale sale) {
        
        StoreStock stock = storeStockRepository.findByProductId(sale.getProductId())
            .orElseThrow(() -> new RuntimeException("Məhsul mağazada yoxdur"));
        
        if (stock.getQuantity().compareTo(sale.getQuantity()) < 0) {
            throw new RuntimeException("Mağazada kifayət qədər məhsul yoxdur. Mağazada: " + stock.getQuantity());
        }
        
        
        sale.setTotalAmount(sale.getUnitPrice().multiply(sale.getQuantity()));
        
       
        stock.setQuantity(stock.getQuantity().subtract(sale.getQuantity()));
        storeStockRepository.save(stock);
        
        return storeSaleRepository.save(sale);
    }
    
    @Transactional
    public void delete(String id) {
        storeSaleRepository.deleteById(id);
    }
}