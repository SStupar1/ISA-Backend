package com.example.demo.controller;

import com.example.demo.dto.response.RegistrationRequestResponse;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IRegistrationRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/registration-request")
public class RegistrationRequestController {

    private IRegistrationRequestService _registrationRequestService;

    public RegistrationRequestController(IRegistrationRequestService registrationRequestService) {
        _registrationRequestService = registrationRequestService;
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public List<RegistrationRequestResponse> getAll() {
        return _registrationRequestService.getAll();
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void approveRegistrationRequest(@PathVariable UUID id) throws Exception {
        _registrationRequestService.approveRegistrationRequest(id);
    }

    @PutMapping("/{id}/deny")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void denyRegistrationRequest(@PathVariable UUID id) throws Exception {
        _registrationRequestService.denyRegistrationRequest(id);
    }
}