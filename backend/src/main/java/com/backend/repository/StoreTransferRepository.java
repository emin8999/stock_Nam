package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.StoreTransfer;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StoreTransferRepository extends JpaRepository<StoreTransfer, String> {
    List<StoreTransfer> findByDateBetween(LocalDate from, LocalDate to);
    List<StoreTransfer> findByProductId(String productId);
}
