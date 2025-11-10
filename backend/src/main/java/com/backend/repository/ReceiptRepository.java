package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.Receipt;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, String> {
    List<Receipt> findByMaterialId(String materialId);
    List<Receipt> findByDateBetween(LocalDate from, LocalDate to);
}
