package com.example.demo.service;

import com.example.demo.dto.request.GradeClinicRequest;
import com.example.demo.dto.request.GradeDoctorRequest;
import com.example.demo.dto.response.AvgGradeResponse;

import java.util.UUID;

public interface IAddGradeService {

    void addGrade(GradeDoctorRequest request) throws Exception;

    void addGrade(GradeClinicRequest request) throws Exception;

    AvgGradeResponse getDoctorsAvgGrade(UUID id);

    AvgGradeResponse getClinicsAvgGrade(UUID id);
}
