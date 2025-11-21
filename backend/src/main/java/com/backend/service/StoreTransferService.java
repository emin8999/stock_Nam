package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.dto.StoreTransferResponseDTO;
import com.backend.entity.Product;
import com.backend.entity.StoreStock;
import com.backend.entity.StoreTransfer;
import com.backend.entity.WarehouseStock;
import com.backend.repository.ProductRepository;
import com.backend.repository.StoreStockRepository;
import com.backend.repository.StoreTransferRepository;
import com.backend.repository.WarehouseStockRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreTransferService {
    
    private final StoreTransferRepository storeTransferRepository;
    private final WarehouseStockRepository warehouseStockRepository;
    private final StoreStockRepository storeStockRepository;
    private final ProductRepository productRepository;
    
    public List<StoreTransfer> findAll() {
        return storeTransferRepository.findAll();
    }
    
    public List<StoreTransfer> findByDateRange(LocalDate from, LocalDate to) {
        return storeTransferRepository.findByDateBetween(from, to);
    }


    @Transactional
    public StoreTransfer create(StoreTransfer transfer) {

    Product product = productRepository.findById(transfer.getProductId())
            .orElseThrow(() -> new RuntimeException("Product tapılmadı"));

    WarehouseStock warehouseStock = warehouseStockRepository.findByProductId(product.getId())
            .orElseThrow(() -> new RuntimeException("Warehouse stock tapılmadı"));

  
    if (warehouseStock.getQuantity().compareTo(transfer.getQuantity()) < 0) {
        throw new RuntimeException("Anbarda kifayət qədər məhsul yoxdur");
    }

    
    warehouseStock.setQuantity(
            warehouseStock.getQuantity().subtract(transfer.getQuantity())
    );
    warehouseStockRepository.save(warehouseStock);

   
    product.setInitialQuantity(
            product.getInitialQuantity().subtract(transfer.getQuantity())
    );
    productRepository.save(product);

  
    StoreStock storeStock = storeStockRepository.findByProductId(product.getId())
            .orElse(new StoreStock(null, product.getId(), BigDecimal.ZERO));

    storeStock.setQuantity(storeStock.getQuantity().add(transfer.getQuantity()));
    storeStockRepository.save(storeStock);

    return storeTransferRepository.save(transfer);
}
    
    @Transactional
    public void delete(String id) {
        storeTransferRepository.deleteById(id);
    }

     public List<StoreTransferResponseDTO> findAllDTO() {
        return storeTransferRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

//     public StoreTransferResponseDTO mapToDTO(StoreTransfer transfer) {
//     Product product = productRepository.findById(transfer.getProductId())
//         .orElseThrow(() -> new RuntimeException("Product not found"));

//     return new StoreTransferResponseDTO(
//         transfer.getId(),
//         transfer.getProductId(),
//         product.getCode(),
//         product.getName(),
//         product.getCategory(),
//         transfer.getQuantity(),
//         transfer.getDate(),
//         transfer.getNote()
//     );
// }

public StoreTransferResponseDTO mapToDTO(StoreTransfer transfer) {
    // orElse(null) istifadə et - xəta atmır
    Product product = productRepository.findById(transfer.getProductId())
        .orElse(null); // ✅ DƏYİŞDİRDİK

    // null check əlavə et
    String code = product != null ? product.getCode() : "DELETED";
    String name = product != null ? product.getName() : "⚠️ Məhsul silinib";
    String category = product != null ? product.getCategory() : "-";

    return new StoreTransferResponseDTO(
        transfer.getId(),
        transfer.getProductId(),
        code,
        name,
        category,
        transfer.getQuantity(),
        transfer.getDate(),
        transfer.getNote()
    );
}
}
