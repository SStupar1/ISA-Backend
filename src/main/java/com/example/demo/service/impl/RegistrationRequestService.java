package com.example.demo.service.impl;

import com.example.demo.dto.request.CreatePatientRequest;
import com.example.demo.dto.request.RegistrationRequest;
import com.example.demo.dto.response.RegistrationRequestResponse;
import com.example.demo.entity.Patient;

import com.example.demo.repository.IPatientRepository;
import com.example.demo.repository.IRegistrationRequestRepository;
import com.example.demo.service.IRegistrationRequestService;
import com.example.demo.utils.enums.RequestStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistrationRequestService implements IRegistrationRequestService {

    private final IRegistrationRequestRepository _registrationRequestRepository;


    public RegistrationRequestService(IRegistrationRequestRepository registrationRequestRepository) {
        _registrationRequestRepository = registrationRequestRepository;
    }
    @Override
    public void createRegistrationRequest(CreatePatientRequest request) {
        RegistrationRequest registrationRequest = mapToRegistrationRequest(request);

        _registrationRequestRepository.save(registrationRequest);
    }

    private RegistrationRequest mapToRegistrationRequest(CreatePatientRequest request) {
        RegistrationRequest result = new RegistrationRequest();
        result.setStatus(RequestStatus.PENDING);
        result.setAddress(request.getAddress());
        result.setCity(request.getCity());
        result.setCountry(request.getCountry());
        result.setEmail(request.getEmail());
        result.setFirstName(request.getFirstName());
        result.setLastName(request.getLastName());
        result.setPhone(request.getPhone());
        result.setSsn(request.getSsn());
        result.setPassword("**********");

        return result;
    }
}