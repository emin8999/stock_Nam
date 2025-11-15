package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dto.WarehouseStockDTO;
import com.backend.entity.Product;
import com.backend.entity.WarehouseAdjustment;
import com.backend.entity.WarehouseStock;
import com.backend.repository.ProductRepository;
import com.backend.repository.WarehouseAdjustmentRepository;
import com.backend.repository.WarehouseStockRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseStockService {
    
    private final WarehouseStockRepository warehouseStockRepository;
    private final WarehouseAdjustmentRepository adjustmentRepository;
    private final ProductRepository productRepository;
    
    public List<WarehouseStockDTO> getStockSnapshot() {
        List<WarehouseStock> stocks = warehouseStockRepository.findAll();
        
        return stocks.stream()
            .map(stock -> {
                Product product = productRepository.findById(stock.getProductId()).orElse(null);
                if (product == null) return null;
                
                return new WarehouseStockDTO(
                    product.getId(),
                    product.getCode(),
                    product.getName(),
                    product.getCategory(),
                    stock.getQuantity()
                );
            })
            .filter(dto -> dto != null)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public WarehouseAdjustment adjustStock(WarehouseAdjustment adjustment) {
        
        WarehouseStock stock = warehouseStockRepository.findByProductId(adjustment.getProductId())
            .orElse(new WarehouseStock(null, adjustment.getProductId(), BigDecimal.ZERO));
        
        if (adjustment.getType() == WarehouseAdjustment.Type.add) {
            stock.setQuantity(stock.getQuantity().add(adjustment.getQuantity()));
        } else if (adjustment.getType() == WarehouseAdjustment.Type.subtract) {
            BigDecimal newQty = stock.getQuantity().subtract(adjustment.getQuantity());
            if (newQty.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("Anbarda kifayət qədər məhsul yoxdur");
            }
            stock.setQuantity(newQty);
        }
        
        warehouseStockRepository.save(stock);
        return adjustmentRepository.save(adjustment);
    }
    
    public List<WarehouseAdjustment> getAllAdjustments() {
        return adjustmentRepository.findAll();
    }
}
