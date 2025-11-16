package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.WarehouseStock;

import java.util.Optional;

@Repository
public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, String> {
    Optional<WarehouseStock> findByProductId(String productId);
    
}
