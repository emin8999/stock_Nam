package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.Finance;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, String> {
    List<Finance> findByDateBetween(LocalDate from, LocalDate to);
    List<Finance> findByType(Finance.FinanceType type);
}
