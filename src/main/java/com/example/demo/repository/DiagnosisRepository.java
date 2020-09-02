package com.example.demo.repository;

import com.example.demo.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiagnosisRepository extends JpaRepository<Diagnosis,Long> {
    List<Diagnosis> findAll();
    Diagnosis findByName(String name);
    Diagnosis findOneById(UUID id);
}
