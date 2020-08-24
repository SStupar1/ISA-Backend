package com.example.demo.service.impl;

import com.example.demo.dto.request.CreatePatientRequest;
import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.PatientFilterRequest;
import com.example.demo.dto.request.UpdatePatientRequest;
import com.example.demo.dto.response.*;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.IPatientService;
import com.example.demo.service.IRegistrationRequestService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.enums.UserType;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    private final IPatientRepository _patientRepository;

    private final IUserService _userService;

    private final IUserRepository _userRepository;


    private final IRegistrationRequestService _registrationRequestService;


    public PatientService(IPatientRepository patientRepository, IUserService userService,
                          IUserRepository userRepository,  IRegistrationRequestService registrationRequestService) {
        _patientRepository = patientRepository;
        _userService = userService;
        _userRepository = userRepository;
        _registrationRequestService = registrationRequestService;
    }

    @Override
    public PatientResponse createPatient(CreatePatientRequest genericRequest) throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword(genericRequest.getPassword());
        userRequest.setRePassword(genericRequest.getRePassword());
        userRequest.setAddress(genericRequest.getAddress());
        userRequest.setCity(genericRequest.getCity());
        userRequest.setEmail(genericRequest.getEmail());
        userRequest.setCountry(genericRequest.getCountry());
        userRequest.setFirstName(genericRequest.getFirstName());
        userRequest.setLastName(genericRequest.getLastName());
        userRequest.setSsn(genericRequest.getSsn());
        userRequest.setPhone(genericRequest.getPhone());
        userRequest.setUserType(UserType.PATIENT);
        // Save user to database and gets NOT User Entity, we get UserResponse
        UserResponse userResponse = _userService.createUser(userRequest);
        userResponse.setDeleted(false);
        // Transform to user entity
        User user = _userRepository.findOneById(userResponse.getId());
        //user.setId(userResponse.getId());

        Patient patient = new Patient();
        patient.setUser(user);
        patient.setActive(false);

        Patient savedPatient = _patientRepository.save(patient);

        _registrationRequestService.createRegistrationRequest(genericRequest);

        return mapPatientToPatientResponse(savedPatient);
    }

    @Override
    public PatientResponse updatePatient(UUID id, UpdatePatientRequest request) throws Exception {
        Patient patient = _patientRepository.findOneById(id);

        if (patient == null) {
            throw new Exception(String.format("Patient with %s id is not found", id));
        }

        // Set values for patient
        // there is no patient fields

        User user = patient.getUser();
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        Patient savedPatient = _patientRepository.save(patient);

        return mapPatientToPatientResponse(savedPatient);
    }

    @Override
    public PatientResponse getPatient(UUID id) throws Exception {
        Patient patient = _patientRepository.findOneById(id);

        if (patient == null) {
            throw new Exception(String.format("Patient with %s id is not found", id));
        }

        return mapPatientToPatientResponse(patient);
    }

    @Override
    public List<PatientResponse> getPatients(PatientFilterRequest patientFilterRequest) {
        List<Patient> patients = _patientRepository.findAllByUser_IsDeleted(false);

        return patients
                .stream()
                .filter(patient -> {
                    if(patientFilterRequest.getFirstName() != null) {
                        return patient.getUser().getFirstName().toLowerCase().contains(patientFilterRequest.getFirstName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .filter(patient -> {
                    if(patientFilterRequest.getLastName() != null) {
                        return patient.getUser().getLastName().toLowerCase().contains(patientFilterRequest.getLastName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .filter(patient -> {
                    if(patientFilterRequest.getSsn() != null) {
                        return patient.getUser().getSsn().toLowerCase().contains(patientFilterRequest.getSsn().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .map(patient -> mapPatientToPatientResponse(patient))
                .collect(Collectors.toList());

    }

    @Override
    public void deletePatient(UUID id) {
        Patient patient = _patientRepository.findOneById(id);
        User user = patient.getUser();
        user.setDeleted(true);
        _patientRepository.save(patient);
    }

    private PatientResponse mapPatientToPatientResponse(Patient patient) {
        PatientResponse patientResponse = new PatientResponse();
        User user = patient.getUser();
        patientResponse.setEmail(user.getEmail());
        patientResponse.setId(patient.getId());
        patientResponse.setAddress(user.getAddress());
        patientResponse.setCity(user.getCity());
        patientResponse.setCountry(user.getCountry());
        patientResponse.setFirstName(user.getFirstName());
        patientResponse.setLastName(user.getLastName());
        patientResponse.setPhone(user.getPhone());
        patientResponse.setSsn(user.getSsn());
        patientResponse.setActive(patient.getActive());
        return patientResponse;
    }

    private ClinicResponse mapClinicToClinicResponse(Clinic clinic) {
        ClinicResponse clinicResponse = new ClinicResponse();
        //ClinicCenter clinicCenter = clinic.getClinicCenter();
        clinicResponse.setId(clinic.getId());
        clinicResponse.setName(clinic.getName());
        clinicResponse.setAddress(clinic.getAddress());
        clinicResponse.setDescription(clinic.getDescription());
        return clinicResponse;
    }
}
