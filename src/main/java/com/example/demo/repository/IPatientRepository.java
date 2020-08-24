package com.example.demo.repository;

import com.example.demo.entity.Clinic;
import com.example.demo.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, UUID> {
    Patient findOneById(UUID id);

    List<Patient> findAllByClinics(Clinic clinic);

    List<Patient> findAllByClinicsAndUser_IsDeleted(Clinic clinic, boolean isDeleted);

    Patient findOneByUser_Email(String email);

    List<Patient> findAllByUser_IsDeleted(boolean isDeleted);
}