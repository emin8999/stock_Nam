package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entity.Product;
import com.backend.entity.WarehouseStock;
import com.backend.repository.ProductRepository;
import com.backend.repository.WarehouseStockRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final WarehouseStockRepository warehouseStockRepository;
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public List<Product> findActive() {
        return productRepository.findByIsActiveTrue();
    }
    
    public Product findById(String id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }
    
    // @Transactional
    // public Product create(Product product) {
    //     return productRepository.save(product);
    // }
    
    @Transactional
    public Product create(Product product) {
    Product saved = productRepository.save(product);

    
    WarehouseStock warehouseStock = new WarehouseStock();
    warehouseStock.setProductId(saved.getId());
    warehouseStock.setQuantity(BigDecimal.ZERO);  
    warehouseStockRepository.save(warehouseStock);

    return saved;
    
    }

    @Transactional
    public Product updateProductQuantity(String productId, BigDecimal newQuantity) {
       
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Məhsul tapılmadı: " + productId));
        
      
        product.setInitialQuantity(newQuantity);
        Product savedProduct = productRepository.save(product);
        
       
        WarehouseStock stock = warehouseStockRepository.findByProductId(productId)
            .orElse(new WarehouseStock(null, productId, BigDecimal.ZERO));
        
        stock.setQuantity(newQuantity);
        warehouseStockRepository.save(stock);
        
        return savedProduct;
    }


    @Transactional
    public Product update(String id, Product product) {
        Product existing = findById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setSalePrice(product.getSalePrice());
        existing.setCategory(product.getCategory());
        existing.setIsActive(product.getIsActive());
        return productRepository.save(existing);
    }
    
    @Transactional
    public void delete(String id) {
        productRepository.deleteById(id);
    }
}
