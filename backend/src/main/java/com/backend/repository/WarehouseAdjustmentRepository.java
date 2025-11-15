package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.WarehouseAdjustment;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WarehouseAdjustmentRepository extends JpaRepository<WarehouseAdjustment, String> {
    List<WarehouseAdjustment> findByDateBetween(LocalDate from, LocalDate to);
    List<WarehouseAdjustment> findByProductId(String productId);
}
