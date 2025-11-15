package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.StoreStock;

import java.util.Optional;

@Repository
public interface StoreStockRepository extends JpaRepository<StoreStock, String> {
    Optional<StoreStock> findByProductId(String productId);
}
