package com.example.demo.service.impl;

import com.example.demo.dto.request.AppointmentTypeFilterRequest;
import com.example.demo.dto.request.CreateAppointmentTypeRequest;
import com.example.demo.dto.request.UpdateAppointmentTypeRequest;
import com.example.demo.dto.response.AppointmentTypeResponse;
import com.example.demo.entity.AppointmentType;
import com.example.demo.entity.ErAppointmentPeriod;
import com.example.demo.repository.IAppointmentTypeRepository;
import com.example.demo.repository.IErAppointmentPeriodRepository;
import com.example.demo.service.IAppointmentTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentTypeService implements IAppointmentTypeService {

    private final IAppointmentTypeRepository _appointmentTypeRepository;

    private final IErAppointmentPeriodRepository _erAppointmentPeriodRepository;

    public AppointmentTypeService(IAppointmentTypeRepository appointmentTypeRepository, IErAppointmentPeriodRepository erAppointmentPeriodRepository) {
        _appointmentTypeRepository = appointmentTypeRepository;
        _erAppointmentPeriodRepository = erAppointmentPeriodRepository;
    }

    @Override
    public List<AppointmentTypeResponse> getAllAppointmentTypes() {
        List<AppointmentType> appointments = _appointmentTypeRepository.findAllByIsDeleted(false);

        return appointments.stream().map(appointmentType -> mapAppointmentTypeToAppointmentTypeResponse(appointmentType)).collect(Collectors.toList());
    }

    @Override
    public AppointmentTypeResponse createAppointmentType(CreateAppointmentTypeRequest request) {
        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setName(request.getName());
        appointmentType.setDeleted(false);
        appointmentType.setPrice(request.getPrice());
        AppointmentType savedAppointmentType = _appointmentTypeRepository.save(appointmentType);

        return mapAppointmentTypeToAppointmentTypeResponse(savedAppointmentType);
    }

    @Override
    public AppointmentTypeResponse updateAppointmentType(UUID id, UpdateAppointmentTypeRequest request) {
        AppointmentType appointmentType = _appointmentTypeRepository.findOneById(id);

        List <ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAll();
        for(ErAppointmentPeriod eap: erAppointmentPeriods) {
            if(eap.getCalendar().getMedicalStaff().getAppointmentType().equals(appointmentType)) {
                return mapAppointmentTypeToAppointmentTypeResponse(appointmentType);
            }
        }
        appointmentType.setName(request.getName());
        AppointmentType savedAppointmentType = _appointmentTypeRepository.save(appointmentType);

        return mapAppointmentTypeToAppointmentTypeResponse(savedAppointmentType);
    }

    @Override
    public AppointmentTypeResponse getAppointmentType(UUID id) {
        AppointmentType appointmentType = _appointmentTypeRepository.findOneById(id);

        return mapAppointmentTypeToAppointmentTypeResponse(appointmentType);
    }

    @Override
    public void deleteAppointmentType(UUID id) {
        AppointmentType appointmentType = _appointmentTypeRepository.findOneById(id);
        List <ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAll();
        for(ErAppointmentPeriod eap: erAppointmentPeriods) {
            if(eap.getCalendar().getMedicalStaff().getAppointmentType().equals(appointmentType)) {
                return;
            }
        }

        appointmentType.setDeleted(true);
        _appointmentTypeRepository.save(appointmentType);
    }

    @Override
    public List<AppointmentTypeResponse> getAllAppointmentTypes(AppointmentTypeFilterRequest request) {
        List<AppointmentType> appointmentTypes = _appointmentTypeRepository.findAllByIsDeleted(false);

        return appointmentTypes
                .stream()
                .filter(medicalStaff -> {
                    if(medicalStaff.getName() != null) {
                        return medicalStaff.getName().toLowerCase().contains(request.getName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .map(medicalStaff -> mapAppointmentTypeToAppointmentTypeResponse(medicalStaff))
                .collect(Collectors.toList());

    }

    public AppointmentTypeResponse mapAppointmentTypeToAppointmentTypeResponse(AppointmentType appointmentType) {
        AppointmentTypeResponse appointmentTypeResponse = new AppointmentTypeResponse();

        appointmentTypeResponse.setId(appointmentType.getId());
        appointmentTypeResponse.setName(appointmentType.getName());
        appointmentTypeResponse.setPrice(appointmentType.getPrice());

        return appointmentTypeResponse;
    }
}
