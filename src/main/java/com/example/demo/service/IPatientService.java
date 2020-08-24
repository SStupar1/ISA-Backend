package com.example.demo.service;

import com.example.demo.dto.request.CreatePatientRequest;
import com.example.demo.dto.request.PatientFilterRequest;
import com.example.demo.dto.request.UpdatePatientRequest;
import com.example.demo.dto.response.*;

import java.util.List;
import java.util.UUID;

public interface IPatientService {

    PatientResponse createPatient(CreatePatientRequest request) throws Exception;

    PatientResponse updatePatient(UUID id, UpdatePatientRequest request) throws Exception;

    PatientResponse getPatient(UUID id) throws Exception;

    List<PatientResponse> getPatients(PatientFilterRequest patientFilterRequest);

    void deletePatient(UUID id);

}
