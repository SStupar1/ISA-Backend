package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.AppointmentRequestNewResponse;
import com.example.demo.dto.response.AppointmentRequestResponse;
import com.example.demo.dto.response.ErResponse;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IAppointmentRequestService;
import com.example.demo.service.IPotentialAppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointment-request")
public class AppointmentRequestController {

    private final IAppointmentRequestService _appointmentRequestService;

    private final IPotentialAppointmentService _potentialAppointmentService;


    public AppointmentRequestController(IAppointmentRequestService appointmentRequestService, IPotentialAppointmentService potentialAppointmentService) {
        _appointmentRequestService = appointmentRequestService;
        _potentialAppointmentService = potentialAppointmentService;
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<AppointmentRequestResponse> getAll() {
        return _appointmentRequestService.getAll();
    }

    @GetMapping("clinic/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<AppointmentRequestNewResponse> getAllByClinic(@PathVariable UUID id) {
        return _appointmentRequestService.getAllByClinic(id);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public void approveAppointmentRequest(@PathVariable UUID id, @RequestBody ConfirmAppointmentRequest request) throws Exception {
        _appointmentRequestService.approveAppointmentRequest(id, request);
    }

    @PutMapping("/{id}/deny")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public void denyAppointmentRequest(@PathVariable UUID id) throws Exception {
        _appointmentRequestService.denyAppointmentRequest(id);
    }

    @PostMapping("/send-request")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public void sendAppointmentRequest(@RequestBody CreateAppointmentRequestRequest request) {
        _appointmentRequestService.createAppointmentRequest(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public AppointmentRequestNewResponse getAppointmentRequest(@PathVariable UUID id) {
        return _appointmentRequestService.getAppointmentRequest(id);
    }

    @GetMapping("search-ers")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<ErResponse> getAvailableErs(ErAvailableRequest request) { //filter je nema request body
        return _appointmentRequestService.getAvailableErs(request);
    }

    @PutMapping("send-request-by-admin/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void sendAppointmentRequestByAdmin(@PathVariable UUID id, @RequestBody SendRequestByAdminRequest request) {
        _appointmentRequestService.createAppointmentRequestByAdmin(id, request.getErId());
    }

    @PutMapping("send-negative-request-by-admin/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void denyAppointmentRequestByAdmin(@PathVariable UUID id) {
        _appointmentRequestService.denyAppointmentRequestByAdmin(id);
    }

    @PostMapping("create-potential-appointment")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void createPotentialAppointmentByDoctor(@RequestBody CreateErAppointmentRequest request) {
        _potentialAppointmentService.createPotentialAppointmentByDoctor(request);
    }

    @GetMapping("potential-appointments")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<AppointmentRequestNewResponse> getAllPotentialAppointments() {
        return _potentialAppointmentService.getAllPotentialAppointmentRequest();
    }
}
