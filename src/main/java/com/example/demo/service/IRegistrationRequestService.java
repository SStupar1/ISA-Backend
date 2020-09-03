package com.example.demo.service;

import com.example.demo.dto.request.CreatePatientRequest;
import com.example.demo.dto.response.RegistrationRequestResponse;

import java.util.List;
import java.util.UUID;

public interface IRegistrationRequestService {

    List<RegistrationRequestResponse> getAll();

    void approveRegistrationRequest(UUID id) throws Exception;

    void denyRegistrationRequest(UUID id) throws Exception;

    void createRegistrationRequest(CreatePatientRequest request);
}
