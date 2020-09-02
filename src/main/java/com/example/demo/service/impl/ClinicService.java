package com.example.demo.service.impl;

import com.example.demo.dto.request.ClinicFilterRequest;
import com.example.demo.dto.request.CreateClinicCenterRequest;
import com.example.demo.dto.request.CreateClinicRequest;
import com.example.demo.dto.request.UpdateClinicRequest;
import com.example.demo.dto.response.ClinicCenterResponse;
import com.example.demo.dto.response.ClinicResponse;
import com.example.demo.entity.Clinic;
import com.example.demo.entity.ClinicCenter;
import com.example.demo.entity.ErAppointmentPeriod;
import com.example.demo.repository.IClinicCenterRepository;
import com.example.demo.repository.IClinicRepository;
import com.example.demo.repository.IErAppointmentPeriodRepository;
import com.example.demo.service.IClinicCenterService;
import com.example.demo.service.IClinicService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClinicService implements IClinicService {

    private final IClinicRepository _clinicRepository;

    private  final IClinicCenterRepository _clinicCenterRepository;

    private final IClinicCenterService _clinicCenterService;

    private final IErAppointmentPeriodRepository _erAppointmentPeriodRepository;

    public ClinicService(IClinicRepository clinicRepository, IClinicCenterRepository clinicCenterRepository, IClinicCenterService clinicCenterService, IErAppointmentPeriodRepository erAppointmentPeriodRepository) {
        _clinicRepository = clinicRepository;
        _clinicCenterRepository = clinicCenterRepository;
        _clinicCenterService = clinicCenterService;
        _erAppointmentPeriodRepository = erAppointmentPeriodRepository;
    }

    @Override
    public ClinicResponse createClinic(CreateClinicRequest request) throws Exception {

        CreateClinicCenterRequest clinicCenterRequest = new CreateClinicCenterRequest();

        // Save user to database and gets NOT User Entity, we get UserResponse
        ClinicCenterResponse clinicCenterResponse = _clinicCenterService.createClinicCenter(clinicCenterRequest);
        // Transform to user entity
        ClinicCenter clinicCenter = _clinicCenterRepository.findOneById(clinicCenterResponse.getId());

        Clinic clinic = new Clinic();
        clinic.setClinicCenter(clinicCenter);
        clinic.setDescription(request.getDescription());
        clinic.setAddress(request.getAddress());
        clinic.setName(request.getName());

        clinic.setDeleted(false);

        Clinic savedClinic = _clinicRepository.save(clinic);

        return mapClinicToClinicResponse(savedClinic);
    }

    @Override
    public ClinicResponse updateClinic(UUID id, UpdateClinicRequest request) throws Exception {
        Clinic clinic = _clinicRepository.findOneById(id);

        if (clinic == null) {
            throw new Exception(String.format("Clinic with %s id is not found", id));
        }
        List <ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAll();
        for(ErAppointmentPeriod eap: erAppointmentPeriods) {
            if(eap.getCalendar().getMedicalStaff().getClinic().equals(clinic)) {
                return mapClinicToClinicResponse(clinic);
            }
        }
        // Set values for patient
        // there is no patient fields

        clinic.setAddress(request.getAddress());
        clinic.setDescription(request.getDescription());
        clinic.setName(request.getName());

        Clinic savedClinic = _clinicRepository.save(clinic);

        return mapClinicToClinicResponse(savedClinic);
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

    @Override
    public ClinicResponse getClinic(UUID id) throws Exception {
        Clinic clinic = _clinicRepository.findOneById(id);

        if (clinic == null) {
            throw new Exception(String.format("Clinic with %s id is not found", id));
        }

        return mapClinicToClinicResponse(clinic);
    }

    @Override
    public List<ClinicResponse> getAllCLinics() {
        List<Clinic> clinics = _clinicRepository.findAllByIsDeleted(false);

        return clinics
                .stream()
                .map(c -> mapClinicToClinicResponse(c))
                .collect(Collectors.toList());
    }

    public List<ClinicResponse> getAllClinics(ClinicFilterRequest request) {
        List<Clinic> clinics = _clinicRepository.findAllByIsDeleted(false);

        return clinics
                .stream()
                .filter(clinic -> {
                    if(request.getName() != null) {
                        return clinic.getName().toLowerCase().contains(request.getName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .filter(clinic -> {
                    if(request.getAddress() != null) {
                        return clinic.getAddress().toLowerCase().contains(request.getAddress().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .map(c -> mapClinicToClinicResponse(c))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteClinic(UUID id) {
        Clinic clinic = _clinicRepository.findOneById(id);
        List <ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAll();
        for(ErAppointmentPeriod eap: erAppointmentPeriods) {
            if(eap.getCalendar().getMedicalStaff().getClinic().equals(clinic)) {
                return;
            }
        }
        clinic.setDeleted(true);
        _clinicRepository.save(clinic);
    }
}
