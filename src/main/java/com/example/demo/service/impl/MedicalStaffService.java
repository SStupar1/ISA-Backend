package com.example.demo.service.impl;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.MedicalStaffResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.IMedicalStaffService;
import com.example.demo.service.IUserService;
import com.example.demo.util.enums.UserType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedicalStaffService implements IMedicalStaffService {

    private final IMedicalStaffRepository _medicalStaffRepository;

    private final IUserService _userService;

    private final IUserRepository _userRepository;

    private  final IClinicRepository _clinicRepository;

    private final IAppointmentTypeRepository _appointmentTypeRepository;

    private final IErAppointmentPeriodRepository _erAppointmentPeriodRepository;

    public MedicalStaffService(IMedicalStaffRepository medicalStaffRepository, IUserService userService, IUserRepository userRepository, IClinicRepository clinicRepository, IAppointmentTypeRepository appointmentTypeRepository, IErAppointmentPeriodRepository erAppointmentPeriodRepository) {
        _medicalStaffRepository = medicalStaffRepository;
        _userService = userService;
        _userRepository = userRepository;
        _clinicRepository = clinicRepository;
        _appointmentTypeRepository = appointmentTypeRepository;
        _erAppointmentPeriodRepository = erAppointmentPeriodRepository;
    }

    @Override
    public MedicalStaffResponse createMedicalStaff(CreateMedicalStaffRequest genericRequest) throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest();

        userRequest.setPassword(genericRequest.getPassword());
        userRequest.setRePassword(genericRequest.getPassword());
        userRequest.setAddress(genericRequest.getAddress());
        userRequest.setCity(genericRequest.getCity());
        userRequest.setEmail(genericRequest.getEmail());
        userRequest.setCountry(genericRequest.getCountry());
        userRequest.setFirstName(genericRequest.getFirstName());
        userRequest.setLastName(genericRequest.getLastName());
        userRequest.setSsn(genericRequest.getSsn());
        userRequest.setPhone(genericRequest.getPhone());
        userRequest.setUserType(UserType.MEDICAL);
        // Save user to database and gets NOT User Entity, we get UserResponse
        UserResponse userResponse = _userService.createUser(userRequest);
        userResponse.setDeleted(false);
        // Transform to user entity
        User user = _userRepository.findOneById(userResponse.getId());
        //user.setId(userResponse.getId());

        MedicalStaff medicalStaff = new MedicalStaff();
        medicalStaff.setUser(user);
        medicalStaff.setMedicalType(genericRequest.getMedicalType());
        Clinic clinic = _clinicRepository.findOneById(genericRequest.getClinicId());
        medicalStaff.setClinic(clinic);
        //medicalStaff.setMedicalType(genericRequest.getMedicalType());
//        medicalStaff.setStartWorkAt(LocalTime.parse("07:30:00"));
//        medicalStaff.setStartWorkAt(LocalTime.parse("16:30:00"));
        medicalStaff.setStartWorkAt(genericRequest.getStartWorkAt());
        medicalStaff.setEndWorkAt(genericRequest.getEndWorkAt());
        medicalStaff.setAppointmentType(_appointmentTypeRepository.findOneById(genericRequest.getAppointmentTypeId()));
        MedicalStaff savedMedicalStaff = _medicalStaffRepository.save(medicalStaff);

        return mapMedicalToMedicalResponse(savedMedicalStaff);
    }

    @Override
    public MedicalStaffResponse updateMedicalStaff(UUID id, UpdateMedicalStaffRequest request) throws Exception {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);

        if (medicalStaff == null) {
            throw new Exception(String.format("Medical with %s id is not found", id));
        }

        List <ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAll();
        for(ErAppointmentPeriod eap: erAppointmentPeriods) {
            if(eap.getCalendar().getMedicalStaff().equals(medicalStaff)) {
                return mapMedicalToMedicalResponse(medicalStaff);
            }
        }

        // Set values for patient
        // there is no patient fields

        User user = medicalStaff.getUser();
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        MedicalStaff savedMedical = _medicalStaffRepository.save(medicalStaff);

        return mapMedicalToMedicalResponse(savedMedical);
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

    @Override
    public MedicalStaffResponse getMedicalStaff(UUID id) throws Exception {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);

        if (medicalStaff == null) {
            throw new Exception(String.format("Medical with %s id is not found", id));
        }

        return mapMedicalToMedicalResponse(medicalStaff);
    }



    @Override
    public List<MedicalStaffResponse> getAllMedicalStaff() {
        List<MedicalStaff> medicalStaffs = _medicalStaffRepository.findAllByUser_IsDeleted(false);

        return medicalStaffs
                .stream()
                .map(ms -> mapMedicalToMedicalResponse(ms))
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalStaffResponse> getAllMedicalStaffByClinic(UUID id) throws Exception {
        Clinic clinic = _clinicRepository.findOneById(id);
        if(clinic == null)
        {
            throw new Exception(String.format("Clinic with % id not found", id));
        }

        List<MedicalStaff> medicals = _medicalStaffRepository.findAllByClinicAndUser_IsDeleted(clinic, false);

        return medicals
                .stream()
                .map(medical -> mapMedicalToMedicalResponse(medical))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMedicalStaff(UUID id) {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);
        User user = medicalStaff.getUser();
//        if(!_erAppointmentPeriodRepository.findAnyByCalendar_MedicalStaff(medicalStaff)) {
//            user.setDeleted(true);
//            _medicalStaffRepository.save(medicalStaff);
//        }
//        ErAppointmentPeriod erAppointmentPeriod = _erAppointmentPeriodRepository.findOneByCalendar_MedicalStaff(medicalStaff);
//        if(erAppointmentPeriod !== null) {
//            user.setDeleted(true);
//            _medicalStaffRepository.save(medicalStaff);
//        }

        List <ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAll();
        for(ErAppointmentPeriod eap: erAppointmentPeriods) {
            if(eap.getCalendar().getMedicalStaff().equals(medicalStaff)) {
                return;
            }
        }

        user.setDeleted(true);
        _medicalStaffRepository.save(medicalStaff);
    }

    @Override
    public List<MedicalStaffResponse> getAllMedicalStaffByClinic(ClinicIdRequest requestId, MedicalFilterRequest request) {
        Clinic clinic = _clinicRepository.findOneById(requestId.getClinicId());
        List<MedicalStaff> medicalStaffs = _medicalStaffRepository.findAllByClinicAndUser_IsDeleted(clinic,false);

        return medicalStaffs
                .stream()
                .filter(medicalStaff -> {
                    if(medicalStaff.getUser().getFirstName() != null) {
                        return medicalStaff.getUser().getFirstName().toLowerCase().contains(request.getFirstName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .filter(medicalStaff -> {
                    if(request.getLastName() != null) {
                        return medicalStaff.getUser().getLastName().toLowerCase().contains(request.getLastName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .map(medicalStaff -> mapMedicalToMedicalResponse(medicalStaff))
                .collect(Collectors.toList());

    }

    @Override
    public List<MedicalStaffResponse> getAllByClinic(UUID id, MedicalFilterRequest request) {
        Clinic clinic = _clinicRepository.findOneById(id);
        List<MedicalStaff> medicalStaffs = _medicalStaffRepository.findAllByClinicAndUser_IsDeleted(clinic,false);

        return medicalStaffs
                .stream()
                .filter(medicalStaff -> {
                    if(medicalStaff.getUser().getFirstName() != null) {
                        return medicalStaff.getUser().getFirstName().toLowerCase().contains(request.getFirstName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .filter(medicalStaff -> {
                    if(request.getLastName() != null) {
                        return medicalStaff.getUser().getLastName().toLowerCase().contains(request.getLastName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .map(medicalStaff -> mapMedicalToMedicalResponse(medicalStaff))
                .collect(Collectors.toList());

    }
}
