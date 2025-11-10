package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.entity.Issue;
import com.backend.service.IssueService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IssueController {
    
    private final IssueService issueService;
    
    @GetMapping
    public ResponseEntity<List<Issue>> getAll() {
        return ResponseEntity.ok(issueService.findAll());
    }
    
    @GetMapping("/material/{materialId}")
    public ResponseEntity<List<Issue>> getByMaterial(@PathVariable String materialId) {
        return ResponseEntity.ok(issueService.findByMaterial(materialId));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Issue>> getByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(issueService.findByDateRange(from, to));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Issue> getById(@PathVariable String id) {
        return ResponseEntity.ok(issueService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Issue> create(@RequestBody Issue issue) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(issueService.create(issue));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        issueService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
