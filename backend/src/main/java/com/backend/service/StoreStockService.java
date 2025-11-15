package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.backend.dto.StoreStockDTO;
import com.backend.entity.Product;
import com.backend.entity.StoreStock;
import com.backend.repository.ProductRepository;
import com.backend.repository.StoreStockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreStockService {
    
    private final StoreStockRepository storeStockRepository;
    private final ProductRepository productRepository;
    
    public List<StoreStockDTO> getStockSnapshot() {
        List<StoreStock> stocks = storeStockRepository.findAll();
        
        return stocks.stream()
            .map(stock -> {
                Product product = productRepository.findById(stock.getProductId()).orElse(null);
                if (product == null) return null;
                
                return new StoreStockDTO(
                    product.getId(),
                    product.getCode(),
                    product.getName(),
                    product.getCategory(),
                    stock.getQuantity(),
                    product.getSalePrice()
                );
            })
            .filter(dto -> dto != null)
            .collect(Collectors.toList());
    }
}
