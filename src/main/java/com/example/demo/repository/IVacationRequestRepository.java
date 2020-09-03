package com.example.demo.repository;

import com.example.demo.entity.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IVacationRequestRepository extends JpaRepository<VacationRequest, UUID> {
    VacationRequest findOneById(UUID id);
}
