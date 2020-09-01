package com.example.demo.repository;

import com.example.demo.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IClinicRepository extends JpaRepository<Clinic, UUID> {
    Clinic findOneById(UUID id);
    List<Clinic> findAllByIsDeleted(boolean isDeleted);
}
