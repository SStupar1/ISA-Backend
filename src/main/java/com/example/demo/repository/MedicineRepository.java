package com.example.demo.repository;

import com.example.demo.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MedicineRepository extends JpaRepository<Medicine,Long> {
    List<Medicine> findAll();
    Medicine findByName(String name);
    Medicine findOneById(UUID id);

}
