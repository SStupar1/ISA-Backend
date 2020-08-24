package com.example.demo.service;

import com.example.demo.dto.request.CreatePatientRequest;
import com.example.demo.dto.response.RegistrationRequestResponse;

import java.util.List;
import java.util.UUID;

public interface IRegistrationRequestService {

    void createRegistrationRequest(CreatePatientRequest request);
}
