package com.example.demo.controller;

import com.example.demo.dto.request.CreatePatientRequest;
import com.example.demo.dto.request.FirstLoginPasswordRequest;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UpdatePasswordRequest;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.PatientResponse;
import java.util.UUID;

import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IAuthService;
import com.example.demo.service.IPatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IPatientService _patientService;

    private final IAuthService _authService;

    public AuthController(IPatientService patientService, IAuthService authService) {
        _patientService = patientService;
        _authService = authService;
    }

    @PostMapping("/patients")
    public PatientResponse createPatient(@RequestBody CreatePatientRequest request) {
        try {
            return _patientService.createPatient(request);
        } catch (Exception ex) {
            return null;
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws Exception {
        return _authService.login(request);
    }

    @PostMapping("/medical/{id}/first-password")
    public LoginResponse loginFirstPasswordMedical(@PathVariable UUID id, @RequestBody FirstLoginPasswordRequest request) {
        try {
            return _authService.setNewPasswordOnFirstLoginMedical(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @PostMapping("/admin/{id}/first-password")
    public LoginResponse loginFirstPasswordAdmin(@PathVariable UUID id, @RequestBody FirstLoginPasswordRequest request) {
        try {
            return _authService.setNewPasswordOnFirstLoginAdmin(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @PutMapping("medical/{id}/update-password")
    public void updatePasswordMedicalStaff(@PathVariable UUID id, @RequestBody UpdatePasswordRequest request) {
        try {
            _authService.updatePasswordMedicalStaff(id, request);
        } catch (Exception ex) {

        }
    }

    @PutMapping("admin/{id}/update-password")
    public void updatePasswordAdmin(@PathVariable UUID id, @RequestBody UpdatePasswordRequest request) {
        try {
            _authService.updatePasswordAdmin(id, request);
        } catch (Exception ex) {

        }
    }

}



