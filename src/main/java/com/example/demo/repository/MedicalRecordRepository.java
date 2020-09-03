package com.example.demo.repository;

import com.example.demo.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {

    MedicalRecord findOneByPatientId(UUID id);
    MedicalRecord findOneById(Long id);

    @Modifying
    @Transactional
    @Query("update MedicalRecord r set r.height = :height , r.weight= :weight, r.diopter= :diopter, r.alergies =:alergies,r.bloodType =:bloodType where r.id = :id")
    int updateMedicalRecord(@Param("height") double height,
                            @Param("weight") double weight,
                            @Param("diopter") double diopter,
                            @Param("alergies") String alergies,
                            @Param("bloodType") String blood_type,
                            @Param("id") Long id);
}
