package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.entity.Settings;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
