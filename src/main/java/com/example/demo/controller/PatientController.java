package com.example.demo.controller;

import com.example.demo.dto.request.PatientFilterRequest;
import com.example.demo.dto.request.UpdatePatientRequest;
import com.example.demo.dto.response.AppointmentRequestNewResponse;
import com.example.demo.dto.response.ErAppointmentPeriodResponse;
import com.example.demo.dto.response.PatientResponse;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.demo.repository.IAppointmentRequestRepository;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IAppointmentRequestService;
import com.example.demo.service.IPatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final IPatientService _patientService;

    private final IAppointmentRequestService _appointmentRequestService;

    private final IAppointmentRequestRepository _appointmentRequestRepository;

    public PatientController(IPatientService patientService, IAppointmentRequestService appointmentRequestService, IAppointmentRequestRepository appointmentRequestRepository) {
        _patientService = patientService;
        _appointmentRequestService = appointmentRequestService;
        _appointmentRequestRepository = appointmentRequestRepository;
    }

    @PutMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public PatientResponse updatePatient(@PathVariable UUID id, @RequestBody UpdatePatientRequest request) {
        try {
            return _patientService.updatePatient(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public PatientResponse getPatient(@PathVariable UUID id) {
        try {
            return _patientService.getPatient(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<PatientResponse> getPatients(PatientFilterRequest patientFilterRequest) {
        return _patientService.getPatients(patientFilterRequest);
    }

    @GetMapping("appointment-requests/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<AppointmentRequestNewResponse> getAllAppointmentRequestsByPatient(@PathVariable UUID id) {
        return _patientService.getAllAppointmentRequestsByPatient(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void deletePatient(@PathVariable UUID id) {
        _patientService.deletePatient(id);
    }

    @GetMapping("/patient/{id}/appointments")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public List<ErAppointmentPeriodResponse> getAllPatientsAppointments(@PathVariable UUID id) {
        return _patientService.getAllAppointments(id);
    }

    @GetMapping("/medical/{id}/patients-by-appointments")
    @PreAuthorize(AuthoritiesConstants.MEDICAL_ROLE)
    public Set<PatientResponse> getAllPatientsByMedicalAppointments(@PathVariable UUID id) {
        return _patientService.getPatientsByAppointments(id);
    }

}
