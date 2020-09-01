package com.example.demo.service;

import com.example.demo.dto.request.ClinicFilterRequest;
import com.example.demo.dto.request.CreateClinicRequest;
import com.example.demo.dto.request.UpdateClinicRequest;
import com.example.demo.dto.response.ClinicResponse;

import java.util.List;
import java.util.UUID;

public interface IClinicService {
    ClinicResponse createClinic(CreateClinicRequest request) throws Exception;

    ClinicResponse updateClinic(UUID id, UpdateClinicRequest request) throws Exception;

    ClinicResponse getClinic(UUID id) throws Exception;

    List<ClinicResponse> getAllCLinics();

    List<ClinicResponse> getAllClinics(ClinicFilterRequest request);

    void deleteClinic(UUID id);
}
