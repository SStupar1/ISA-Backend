package com.example.demo.controller;

import com.example.demo.dto.response.AppointmentPeriodResponse;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IAppointmentPeriodService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/appointment-period")
public class AppointmentPeriodController {

    private IAppointmentPeriodService _appointmentPeriodService;

    public AppointmentPeriodController(IAppointmentPeriodService appointmentPeriodService) {
        _appointmentPeriodService = appointmentPeriodService;
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<AppointmentPeriodResponse> getAllAppointmentPeriods() { return _appointmentPeriodService.getAllAppointmentPeriods(); }

}