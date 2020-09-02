package com.example.demo.controller;

import com.example.demo.dto.request.AppointmentTypeFilterRequest;
import com.example.demo.dto.request.CreateAppointmentTypeRequest;
import com.example.demo.dto.request.UpdateAppointmentTypeRequest;
import com.example.demo.dto.response.AppointmentTypeResponse;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IAppointmentTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointment-type")
public class AppointmentTypeController {
    private final IAppointmentTypeService _appointmentTypeService;

    public AppointmentTypeController(IAppointmentTypeService appointmentTypeService) {
        _appointmentTypeService = appointmentTypeService;
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<AppointmentTypeResponse> getAllAppointmentTypes() { return _appointmentTypeService.getAllAppointmentTypes(); }

    @GetMapping("with-filter")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<AppointmentTypeResponse> getAllAppointmentTypes(AppointmentTypeFilterRequest request) { return _appointmentTypeService.getAllAppointmentTypes(request); }


    @PostMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public AppointmentTypeResponse createAppointmentType(@RequestBody CreateAppointmentTypeRequest request) {
        return _appointmentTypeService.createAppointmentType(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public AppointmentTypeResponse updateAppointmentType(@PathVariable UUID id, @RequestBody UpdateAppointmentTypeRequest request) {
        return _appointmentTypeService.updateAppointmentType(id, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public  AppointmentTypeResponse getAppointmentType(@PathVariable UUID id) {
        return _appointmentTypeService.getAppointmentType(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void deleteAppointmentType(@PathVariable UUID id) {
        _appointmentTypeService.deleteAppointmentType(id);
    }


}
