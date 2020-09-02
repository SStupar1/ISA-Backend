package com.example.demo.service;

import com.example.demo.dto.request.AppointmentTypeFilterRequest;
import com.example.demo.dto.request.CreateAppointmentTypeRequest;
import com.example.demo.dto.request.UpdateAppointmentTypeRequest;
import com.example.demo.dto.response.AppointmentTypeResponse;

import java.util.List;
import java.util.UUID;

public interface IAppointmentTypeService {
    List<AppointmentTypeResponse> getAllAppointmentTypes();

    AppointmentTypeResponse createAppointmentType(CreateAppointmentTypeRequest request);

    AppointmentTypeResponse updateAppointmentType(UUID id, UpdateAppointmentTypeRequest request);

    AppointmentTypeResponse getAppointmentType(UUID id);

    void deleteAppointmentType(UUID id);

    List<AppointmentTypeResponse> getAllAppointmentTypes(AppointmentTypeFilterRequest request);
}