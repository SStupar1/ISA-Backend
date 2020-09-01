package com.example.demo.repository;

import com.example.demo.entity.ClinicCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IClinicCenterRepository extends JpaRepository<ClinicCenter, UUID> {
    ClinicCenter findOneById(UUID id);
}
