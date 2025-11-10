package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.Issue;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {
    List<Issue> findByMaterialId(String materialId);
    List<Issue> findByDateBetween(LocalDate from, LocalDate to);
}
