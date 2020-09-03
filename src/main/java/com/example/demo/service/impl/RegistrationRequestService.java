package com.example.demo.service.impl;

import com.example.demo.dto.request.CreatePatientRequest;
import com.example.demo.dto.response.RegistrationRequestResponse;
import com.example.demo.entity.Patient;
import com.example.demo.entity.RegistrationRequest;
import com.example.demo.repository.IPatientRepository;
import com.example.demo.repository.IRegistrationRequestRepository;
import com.example.demo.service.IEmailService;
import com.example.demo.service.IRegistrationRequestService;
import com.example.demo.util.enums.RequestStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistrationRequestService implements IRegistrationRequestService {

    private final IRegistrationRequestRepository _registrationRequestRepository;

    private final IEmailService _emailService;

    private final IPatientRepository _patientRepository;

    public RegistrationRequestService(IRegistrationRequestRepository registrationRequestRepository, IEmailService emailService, IPatientRepository patientRepository) {
        _registrationRequestRepository = registrationRequestRepository;
        _emailService = emailService;
        _patientRepository = patientRepository;
    }

    @Override
    public List<RegistrationRequestResponse> getAll() {
        List<RegistrationRequest> requests = _registrationRequestRepository.findAll();

        return requests.stream().map(request -> mapToRegistrationResponse(request)).collect(Collectors.toList());
    }

    @Override
    public void approveRegistrationRequest(UUID id) throws Exception {
        RegistrationRequest request = _registrationRequestRepository.findOneById(id);

        if (request == null) {
            throw new Exception(String.format("Request with %s id not found", id.toString()));
        }

        request.setStatus(RequestStatus.APPROVED);
        _registrationRequestRepository.save(request);

        Patient patient = _patientRepository.findOneByUser_Email(request.getEmail());
        patient.setActive(true);
        _patientRepository.save(patient);

        _emailService.sendAcceptedMailToPatient(patient.getUser());
    }

    @Override
    public void denyRegistrationRequest(UUID id) throws Exception {
        RegistrationRequest request = _registrationRequestRepository.findOneById(id);

        if (request == null) {
            throw new Exception(String.format("Request with %s id not found", id.toString()));
        }

        request.setStatus(RequestStatus.DENIED);
        _registrationRequestRepository.save(request);

        Patient patient = _patientRepository.findOneByUser_Email(request.getEmail());
        patient.setActive(false);
        _patientRepository.save(patient);

        _emailService.sendADeniedMailToPatient(patient.getUser());
    }

    @Override
    public void createRegistrationRequest(CreatePatientRequest request) {
        RegistrationRequest registrationRequest = mapToRegistrationRequest(request);

        _registrationRequestRepository.save(registrationRequest);
    }

    private RegistrationRequestResponse mapToRegistrationResponse(RegistrationRequest registrationRequest) {
        RegistrationRequestResponse regReq = new RegistrationRequestResponse();
        regReq.setEmail(registrationRequest.getEmail());
        regReq.setFirstName(registrationRequest.getFirstName());
        regReq.setLastName(registrationRequest.getLastName());
        regReq.setCountry(registrationRequest.getCountry());
        regReq.setCity(registrationRequest.getCity());
        regReq.setAddress(registrationRequest.getAddress());
        regReq.setPhone(registrationRequest.getPhone());
        regReq.setSsn(registrationRequest.getSsn());
        regReq.setStatus(registrationRequest.getStatus());
        regReq.setId(registrationRequest.getId());
        return regReq;
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
