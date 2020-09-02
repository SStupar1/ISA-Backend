package com.example.demo.dto.response;

import com.example.demo.entity.MedicalExaminationReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalExaminationDTOResponse {

    private UUID patient;
    private UUID doctor;
    private String description;
    private String diagnosis;
    private String medicine;

    public MedicalExaminationDTOResponse(MedicalExaminationReport m){
        this(m.getPatient().getId(),m.getDoctor().getId(),m.getDescription(),m.getDiagnosis().getName(),m.getMedicine().getName());
    }
}