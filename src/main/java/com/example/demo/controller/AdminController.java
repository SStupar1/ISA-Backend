package com.example.demo.controller;

import com.example.demo.dto.request.*;
import com.example.demo.dto.response.AdminResponse;
import com.example.demo.dto.response.MedicalStaffResponse;
import com.example.demo.repository.IClinicRepository;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IAdminService;
import com.example.demo.service.IAppointmentService;
import com.example.demo.service.IClinicService;
import com.example.demo.service.IMedicalStaffService;
import com.example.demo.util.enums.MedicalType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final IAdminService _adminService;
    private final IClinicRepository _clinicRepository;
    private final IMedicalStaffService _medicalStaffService;
    private final IClinicService _clinicService;
    private final IAppointmentService _appointmentService;

    public AdminController(IAdminService adminService, IClinicRepository clinicRepository, IMedicalStaffService medicalStaffService, IClinicService clinicService, IAppointmentService appointmentService) {
        _adminService = adminService;
        _clinicRepository = clinicRepository;
        _medicalStaffService = medicalStaffService;
        _clinicService = clinicService;
        _appointmentService = appointmentService;
    }

    @PostMapping("suggest-ers")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void suggestEr(@RequestBody SuggestErRequest request) {
        _appointmentService.suggestEr(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public AdminResponse updatePatient(@PathVariable UUID id, @RequestBody UpdateAdminRequest request) {
        try {
            return _adminService.updateAdmin(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @PostMapping("/add-doctor")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public MedicalStaffResponse createDoctor(@RequestBody CreateMedicalStaffRequest request) {
        try {
            request.setMedicalType(MedicalType.DOCTOR);
            MedicalStaffResponse medicalStaffResponse = _medicalStaffService.createMedicalStaff(request);
            return medicalStaffResponse;
        } catch (Exception ex) {
            return null;
        }
    }

    @PostMapping("/add-nurse")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public MedicalStaffResponse createNurse(@RequestBody CreateMedicalStaffRequest request, @PathVariable UUID id) {
        try {
            request.setMedicalType(MedicalType.NURSE);
            MedicalStaffResponse medicalStaffResponse = _medicalStaffService.createMedicalStaff(request);
            return medicalStaffResponse;
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public AdminResponse getAdmin(@PathVariable UUID id) {
        try {
            return _adminService.getAdmin(id);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public List<AdminResponse> getAdmins() {
        return _adminService.getAdmins();
    }

    @PostMapping("/create-admin")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public AdminResponse createAdmin(CreateAdminRequest request) {
        try {
            return _adminService.createAdmin(request);
        } catch (Exception ex) {
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void deleteAdmin(@PathVariable UUID id) {
        _adminService.deleteAdmin(id);
    }
}
