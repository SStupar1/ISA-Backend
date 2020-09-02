package com.example.demo.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class GradeDoctorRequest {

    private UUID doctorId;

    private UUID patientId;

    private String grade;
}