package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.*;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/clinics")
public class ClinicController {

    private  final IClinicService _clinicService;

    private  final IPatientService _patientService;

    private final IMedicalStaffService _medicalStaffService;

    private final IErService _erService;

    private final IAddGradeService _addGradeService;

    public ClinicController(IClinicService clinicService, IPatientService patientService, IMedicalStaffService medicalStaffService, IErService erService, IAddGradeService addGradeService) {
        _clinicService = clinicService;
        _patientService = patientService;
        _medicalStaffService = medicalStaffService;
        _erService = erService;
        _addGradeService = addGradeService;
    }

    @PostMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public ClinicResponse createClinic(@RequestBody CreateClinicRequest request) {
        try {
            return _clinicService.createClinic(request);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ClinicResponse getClinic(@PathVariable UUID id) {
        try {
            return _clinicService.getClinic(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<ClinicResponse> getAllClinics() {
        return _clinicService.getAllCLinics();
    }

    @GetMapping("/with-filter")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<ClinicResponse> getAllClinics(ClinicFilterRequest request) {
        return _clinicService.getAllClinics(request);
    }

    @GetMapping("/{id}/patients")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<PatientResponse> getAllPatientsByClinic(@PathVariable UUID id, PatientFilterRequest patientFilterRequest) throws Exception {
        return _patientService.getAllPatientsByClinic(id, patientFilterRequest);
    }

    @GetMapping("/{id}/medical")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<MedicalStaffResponse> getAllMedicalStaffByClinic(@PathVariable UUID id) throws Exception {
        return _medicalStaffService.getAllMedicalStaffByClinic(id);
    }

    @GetMapping("/{id}/medical-filter")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<MedicalStaffResponse> getAllMedicalStaffByClinic(@PathVariable UUID id, MedicalFilterRequest request) throws Exception {
        return _medicalStaffService.getAllByClinic(id, request);
    }

    @PutMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public ClinicResponse updateClinic(@PathVariable UUID id, @RequestBody UpdateClinicRequest request) {
        try {
            return _clinicService.updateClinic(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}/ers")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<ErResponse> getAllErsByClinic(@PathVariable UUID id) throws Exception {
        return _erService.getAllErsByClinic(id);
    }

    @GetMapping("/{id}/er")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<ErResponse> getAllErsByClinic(@PathVariable UUID id, ErFilterRequest request) {
        return _erService.getAllErsByClinic(id, request);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void deleteClinic(@PathVariable UUID id) {
        _clinicService.deleteClinic(id);
    }

    @PostMapping("grade-a-clinic")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public void gradeAClinic(@RequestBody  GradeClinicRequest request) throws Exception {
        _addGradeService.addGrade(request);
    }

    @GetMapping("my-clinics/patient/{id}")
    @PreAuthorize(AuthoritiesConstants.PATIENT_ROLE)
    public Set<ClinicResponse> getAllMedicalsByAppointmentsByPatients(@PathVariable UUID id) throws Exception{
        return _patientService.getClinicsByAppointments(id);
    }

    @GetMapping("avg/{id}/clinic")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public AvgGradeResponse getClinicsAvgGrade(@PathVariable UUID id) {
        return _addGradeService.getClinicsAvgGrade(id);
    }
}
