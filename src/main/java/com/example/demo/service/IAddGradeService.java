package com.example.demo.service;

import com.example.demo.dto.request.GradeClinicRequest;
import com.example.demo.dto.request.GradeDoctorRequest;

import java.util.UUID;

public interface IAddGradeService {

    void addGrade(GradeDoctorRequest request);

    void addGrade(GradeClinicRequest request);
}
