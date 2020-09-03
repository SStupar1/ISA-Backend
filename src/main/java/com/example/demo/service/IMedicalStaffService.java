package com.example.demo.service;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.MedicalStaffResponse;

import java.util.List;
import java.util.UUID;

public interface IMedicalStaffService {

    MedicalStaffResponse createMedicalStaff(CreateMedicalStaffRequest request) throws Exception;

    MedicalStaffResponse updateMedicalStaff(UUID id, UpdateMedicalStaffRequest request) throws Exception;

    MedicalStaffResponse getMedicalStaff(UUID id) throws Exception;

    List<MedicalStaffResponse> getAllMedicalStaff();

    List<MedicalStaffResponse> getAllMedicalStaffByClinic(UUID id) throws  Exception;

    void deleteMedicalStaff(UUID id);

    List<MedicalStaffResponse> getAllMedicalStaffByClinic(ClinicIdRequest requestId, MedicalFilterRequest request);

    List<MedicalStaffResponse> getAllByClinic(UUID id, MedicalFilterRequest request);
}
