package com.example.demo.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class GradeClinicRequest {

    private UUID patientId;

    private UUID clinicId;

    private String grade;
}
