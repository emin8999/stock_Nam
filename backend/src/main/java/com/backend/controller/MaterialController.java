package com.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.backend.entity.Material;

import com.backend.service.MaterialService;


import java.util.List;


@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MaterialController {
    
    private final MaterialService materialService;
    
    @GetMapping
    public ResponseEntity<List<Material>> getAll() {
        return ResponseEntity.ok(materialService.findAll());
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Material>> getActive() {
        return ResponseEntity.ok(materialService.findActive());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Material> getById(@PathVariable String id) {
        return ResponseEntity.ok(materialService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<Material> create(@RequestBody Material material) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(materialService.create(material));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Material> update(@PathVariable String id, @RequestBody Material material) {
        return ResponseEntity.ok(materialService.update(id, material));
    }
    
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Void> toggleActive(@PathVariable String id) {
        materialService.toggleActive(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        materialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}