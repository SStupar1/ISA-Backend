package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.AvgGradeResponse;
import com.example.demo.dto.response.CalendarResponse;
import com.example.demo.dto.response.MedicalStaffResponse;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IAddGradeService;
import com.example.demo.service.IMedicalStaffService;
import com.example.demo.service.IPatientService;
import com.example.demo.service.IVacationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/medicalstaff")
public class MedicalStaffController {

    private final IMedicalStaffService _medicalStaffService;

    private final IVacationService _vacationService;

    private final IAddGradeService _addGradeService;

    private final IPatientService _patientService;

    public MedicalStaffController(IMedicalStaffService medicalStaffService, IVacationService vacationService, IAddGradeService addGradeService, IPatientService patientService) {
        _medicalStaffService = medicalStaffService;
        _vacationService = vacationService;
        _addGradeService = addGradeService;
        _patientService = patientService;
    }


    @PutMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.MEDICAL_ROLE)
    public MedicalStaffResponse updateMedicalStaff(@PathVariable UUID id, @RequestBody UpdateMedicalStaffRequest request) {
        try {
            return _medicalStaffService.updateMedicalStaff(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public MedicalStaffResponse getMedicalStaff(@PathVariable UUID id) {
        try {
            return _medicalStaffService.getMedicalStaff(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<MedicalStaffResponse> getAllMedicalStaff() {
        return _medicalStaffService.getAllMedicalStaff();
    }

    @GetMapping("/with-filter")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<MedicalStaffResponse> getAllMedicalStaffByClinic(@RequestBody ClinicIdRequest requestId, MedicalFilterRequest request) { return  _medicalStaffService.getAllMedicalStaffByClinic(requestId, request); }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void deleteMedicalStaff(@PathVariable UUID id) {
        _medicalStaffService.deleteMedicalStaff(id);
    }

    @PostMapping("vacation-request")
    @PreAuthorize(AuthoritiesConstants.MEDICAL_ROLE)
    public void vacationRequest(@RequestBody CreateVacationRequest request) {
        _vacationService.sendVacationRequest(request);
    }

    @PostMapping("grade-a-doctor")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public void gradeADoctor(@RequestBody  GradeDoctorRequest request) throws Exception {
        _addGradeService.addGrade(request);
    }

    @GetMapping("avg/{id}/doctor")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public AvgGradeResponse getDoctorsAvgGrade(@PathVariable UUID id) {
        return _addGradeService.getDoctorsAvgGrade(id);
    }

    @GetMapping("my-doctors/patient/{id}")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public Set<MedicalStaffResponse> getAllMedicalsByAppointmentsByPatients(@PathVariable UUID id) {
        return _patientService.getDoctorsByAppointments(id);
    }

    @GetMapping("calendar/{id}")
    @PreAuthorize(AuthoritiesConstants.MEDICAL_ROLE)
    public List<CalendarResponse> getWorkCalendar(@PathVariable UUID id){
        return _medicalStaffService.getWorkCalendar(id);
    }
}

