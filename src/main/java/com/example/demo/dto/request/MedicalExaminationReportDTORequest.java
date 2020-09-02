package com.example.demo.dto.request;

import com.example.demo.entity.MedicalExaminationReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalExaminationReportDTORequest {
    private UUID patient;
    private UUID doctor;
    private String description;
    private UUID diagnosis;
    private UUID medicine;

    public MedicalExaminationReportDTORequest(MedicalExaminationReport medicalExaminationReport){
        this(medicalExaminationReport.getPatient().getId(),
                medicalExaminationReport.getDoctor().getId(),
                medicalExaminationReport.getDescription(),
                medicalExaminationReport.getDiagnosis().getId(),
                medicalExaminationReport.getMedicine().getId());
    }


}
