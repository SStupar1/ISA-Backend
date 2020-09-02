package com.example.demo.repository;
import com.example.demo.entity.MedicalExaminationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface MedicalExaminationReportRepository extends JpaRepository<MedicalExaminationReport,Long> {

    @Query("SELECT mer FROM MedicalExaminationReport mer where mer.patient.id=:id")
    Set<MedicalExaminationReport> findAllPatients(@Param("id") UUID id);
}
