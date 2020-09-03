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
import com.example.demo.util.enums.UserType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    private final IPatientRepository _patientRepository;

    private final IUserService _userService;

    private final IUserRepository _userRepository;

    private final IClinicRepository _clinicReposirotry;

    private final IRegistrationRequestService _registrationRequestService;

    private final IAppointmentRequestRepository _appointmentRequestRepository;

    private final IErAppointmentPeriodRepository _erAppointmentPeriodRepository;

    private final IMedicalStaffRepository _medicalStaffRepository;

    public PatientService(IPatientRepository patientRepository, IUserService userService,
                          IUserRepository userRepository, IClinicRepository clinicReposirotry, IRegistrationRequestService registrationRequestService, IAppointmentRequestRepository appointmentRequestRepository, IErAppointmentPeriodRepository erAppointmentPeriodRepository, IMedicalStaffRepository medicalStaffRepository) {
        _patientRepository = patientRepository;
        _userService = userService;
        _userRepository = userRepository;
        _clinicReposirotry = clinicReposirotry;
        _registrationRequestService = registrationRequestService;
        _appointmentRequestRepository = appointmentRequestRepository;
        _erAppointmentPeriodRepository = erAppointmentPeriodRepository;
        _medicalStaffRepository = medicalStaffRepository;
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
    public List<PatientResponse> getAllPatientsByClinic(UUID id, PatientFilterRequest patientFilterRequest) throws Exception {
        Clinic clinic = _clinicReposirotry.findOneById(id);
        if(clinic == null)
        {
            throw new Exception(String.format("Clinic with % id not found", id));
        }

        List<Patient> patients = _patientRepository.findAllByClinicsAndUser_IsDeleted(clinic, false);

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

//        return patients
//                .stream()
//                .map(patient -> mapPatientToPatientResponse(patient))
//                .collect(Collectors.toList());
    }

    @Override
    public void deletePatient(UUID id) {
        Patient patient = _patientRepository.findOneById(id);
        User user = patient.getUser();
        user.setDeleted(true);
        _patientRepository.save(patient);
    }

    @Override
    public List<AppointmentRequestNewResponse> getAllAppointmentRequestsByPatient(UUID id) {

        List<AppointmentRequest> appointmentRequests = _appointmentRequestRepository.findAllByPatient_Id(id);

                return appointmentRequests
                .stream()
                .map(appointmentRequest -> mapAppointmentRequestToAppointmentRequestNewResponse(appointmentRequest))
                .collect(Collectors.toList());
    }

    @Override
    public List<ErAppointmentPeriodResponse> getAllAppointments(UUID id) {
        List<ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAllByCalendar_PatientId(id);
        return erAppointmentPeriods.stream().map(this::mapErAppointmentPeriodToResponse).collect(Collectors.toList());
    }

    @Override
    public Set<MedicalStaffResponse> getDoctorsByAppointments(UUID id) {
        List<ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAllByCalendar_PatientId(id);
        List<MedicalStaff> medicalStaffs = new ArrayList<>();
        for(ErAppointmentPeriod erAppointmentPeriod : erAppointmentPeriods) {
            medicalStaffs.add(erAppointmentPeriod.getCalendar().getMedicalStaff());
        }
        return medicalStaffs.stream().map(this::mapMedicalToMedicalResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<ClinicResponse> getClinicsByAppointments(UUID id) {
        List<ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAllByCalendar_PatientId(id);
        List<Clinic> clinics = new ArrayList<>();
        for (ErAppointmentPeriod eap: erAppointmentPeriods){
            clinics.add(eap.getCalendar().getMedicalStaff().getClinic());
        }

        return clinics.stream().map(this::mapClinicToClinicResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<PatientResponse> getPatientsByAppointments(UUID id) {
        List<ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAll();
        List<Patient> patients = new ArrayList<>();
//        if(erAppointmentPeriods.isEmpty())
//            return null;
        for(ErAppointmentPeriod eap: erAppointmentPeriods) {
            if(eap.getCalendar().getMedicalStaff().equals(_medicalStaffRepository.findOneById(id)));
                patients.add(_patientRepository.findOneById(eap.getCalendar().getPatientId()));
        }

        return  patients.stream().map(this::mapPatientToPatientResponse).collect(Collectors.toSet());
    }


    private MedicalStaffResponse mapMedicalToMedicalResponse(MedicalStaff medicalStaff) {
        MedicalStaffResponse medicalStaffResponse = new MedicalStaffResponse();
        User user = medicalStaff.getUser();
        medicalStaffResponse.setAddress(user.getAddress());
        medicalStaffResponse.setCity(user.getCity());
        medicalStaffResponse.setCountry(user.getCountry());
        medicalStaffResponse.setEmail(user.getEmail());
        medicalStaffResponse.setFirstName(user.getFirstName());
        medicalStaffResponse.setLastName(user.getLastName());
        medicalStaffResponse.setId(medicalStaff.getId());
        medicalStaffResponse.setPhone(user.getPhone());
        medicalStaffResponse.setSsn(user.getSsn());
        medicalStaffResponse.setMedicalType(medicalStaff.getMedicalType());

//        medicalStaffResponse.setClinic(medicalStaff.getClinic());
//        medicalStaffResponse.setStartWorkAt(medicalStaff.getStartWorkAt());
//        medicalStaffResponse.setEndWorkAt(medicalStaff.getEndWorkAt());

        return medicalStaffResponse;
    }

    private ErAppointmentPeriodResponse mapErAppointmentPeriodToResponse(ErAppointmentPeriod erAppointmentPeriod) {
        ErAppointmentPeriodResponse patientResponse = new ErAppointmentPeriodResponse();

        patientResponse.setDate(erAppointmentPeriod.getDate());
        patientResponse.setAppointmentTypeName(erAppointmentPeriod.getCalendar().getMedicalStaff().getAppointmentType().getName());
        patientResponse.setClinicName(erAppointmentPeriod.getCalendar().getMedicalStaff().getClinic().getName());
        patientResponse.setDoctorName(erAppointmentPeriod.getCalendar().getMedicalStaff().getUser().getFirstName());
        patientResponse.setStartAt(erAppointmentPeriod.getStartAt().toString());

        return patientResponse;
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

    private AppointmentRequestNewResponse mapAppointmentRequestToAppointmentRequestNewResponse(AppointmentRequest request) {
        AppointmentRequestNewResponse appointmentRequestNewResponse = new AppointmentRequestNewResponse();
        appointmentRequestNewResponse.setAppointmentDate(request.getAppointmentDate());
        appointmentRequestNewResponse.setAppointmentTypeName(request.getAppointmentType().getName());
        appointmentRequestNewResponse.setClinicName(request.getClinic().getName());
        appointmentRequestNewResponse.setDoctorName(request.getMedicalStaff().getUser().getFirstName());
        appointmentRequestNewResponse.setStatus(request.getStatus());
        appointmentRequestNewResponse.setLocalTime(request.getStartAt());
        appointmentRequestNewResponse.setClinicId(request.getClinic().getId());
        appointmentRequestNewResponse.setLocalTime2(request.getEndAt());
        appointmentRequestNewResponse.setId(request.getId());
        return appointmentRequestNewResponse;
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
