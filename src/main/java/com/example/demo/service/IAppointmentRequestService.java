package com.example.demo.service;


import com.example.demo.dto.request.*;
import com.example.demo.dto.response.AppointmentRequestNewResponse;
import com.example.demo.dto.response.AppointmentRequestResponse;
import com.example.demo.dto.response.ErResponse;
import com.example.demo.entity.AppointmentRequest;

import java.util.List;
import java.util.UUID;

public interface IAppointmentRequestService {

    List<AppointmentRequestResponse> getAll();

    List<AppointmentRequestNewResponse> getAllByClinic(UUID id);

    void approveAppointmentRequest(UUID id, ConfirmAppointmentRequest request) throws Exception;

    void denyAppointmentRequest(UUID id) throws Exception;

    void createAppointmentRequest(CreateAppointmentRequestRequest request);

    AppointmentRequestNewResponse getAppointmentRequest(UUID id);

    List<ErResponse> getAvailableErs(ErAvailableRequest request);

    void createAppointmentRequestByAdmin(UUID id_request, UUID id_er);

    void denyAppointmentRequestByAdmin(UUID id_request);

    void CreateAppointmentRequestAsDoctor(CreateAppointmentRequestAsDoctorRequest request) throws Exception;
}
