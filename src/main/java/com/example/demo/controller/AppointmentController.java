package com.example.demo.controller;

import com.example.demo.dto.request.AppointmentSearchRequest;
import com.example.demo.dto.request.CreateAppointmentRequestAsDoctorRequest;
import com.example.demo.dto.request.DoctorSearchRequest;
import com.example.demo.dto.request.ScheduleAppointmentRequest;
import com.example.demo.dto.response.AppointmentResponse;
import com.example.demo.dto.response.ClinicResponse;
import com.example.demo.dto.response.MedicalStaffResponse;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IAppointmentRequestService;
import com.example.demo.service.IAppointmentService;
import com.example.demo.service.IPatientService;
import com.example.demo.service.IPotentialAppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final IAppointmentService _appointmentService;

    private final IAppointmentRequestService _appointmentRequestService;

    private final IPatientService _patientService;

    private final IPotentialAppointmentService _potentialAppointmentService;

    public AppointmentController(IAppointmentService appointmentService, IAppointmentRequestService appointmentRequestService, IPatientService patientService, IPotentialAppointmentService potentialAppointmentService) {
        _appointmentService = appointmentService;
        _appointmentRequestService = appointmentRequestService;
        _patientService = patientService;
        _potentialAppointmentService = potentialAppointmentService;
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<AppointmentResponse> getAllAppointments() { return _appointmentService.getAllAppointments(); }

    @GetMapping("/search-appointments")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public Set<ClinicResponse> getClinicResponse(AppointmentSearchRequest request) {
        return _appointmentService.getClinicsByTimeAndAppointemntType(request);
    }

    @GetMapping("/search-doctors")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<MedicalStaffResponse> getMedicalStaffResponse(DoctorSearchRequest request) {
        return _appointmentService.getDoctorsByChosenClinic(request);
    }

    @PostMapping("/create-request-as-doctor")
    @PreAuthorize(AuthoritiesConstants.MEDICAL_ROLE)
    public void createAppointmentRequestAsDoctor(@RequestBody CreateAppointmentRequestAsDoctorRequest request) throws  Exception{
        _appointmentRequestService.CreateAppointmentRequestAsDoctor(request);
    }

    @PostMapping("/schedule-potential-appointment")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public void schedulePotentialAppointment(@RequestBody ScheduleAppointmentRequest request) {
        _potentialAppointmentService.schedulePredefAppointment(request);
    }
}