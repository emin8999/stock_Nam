package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.StoreSale;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StoreSaleRepository extends JpaRepository<StoreSale, String> {
    List<StoreSale> findByDateBetween(LocalDate from, LocalDate to);
    List<StoreSale> findByProductId(String productId);
}
