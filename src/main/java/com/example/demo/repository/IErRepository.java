package com.example.demo.repository;

import com.example.demo.entity.Er;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IErRepository extends JpaRepository<Er, UUID> {

    Er findOneById(UUID id);
    List<Er> findAllByClinic_Id(UUID clinicId);
    List<Er> findAllByClinic_IdAndIsDeleted(UUID clinicId, boolean isDeleted);
    List<Er> findAllByIsDeleted(boolean isDeleted);
}
