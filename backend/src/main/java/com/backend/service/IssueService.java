package com.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.entity.Issue;
import com.backend.repository.IssueRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {
    
    private final IssueRepository issueRepository;
    
    public List<Issue> findAll() {
        return issueRepository.findAll();
    }
    
    public List<Issue> findByMaterial(String materialId) {
        return issueRepository.findByMaterialId(materialId);
    }
    
    public List<Issue> findByDateRange(LocalDate from, LocalDate to) {
        return issueRepository.findByDateBetween(from, to);
    }
    
    public Issue findById(String id) {
        return issueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Issue not found: " + id));
    }
    
    @Transactional
    public Issue create(Issue issue) {
        issue.setTotalCostUsed(issue.getQtyKg().multiply(issue.getAvgCostUsed()));
        return issueRepository.save(issue);
    }
    
    @Transactional
    public void delete(String id) {
        issueRepository.deleteById(id);
    }
}
