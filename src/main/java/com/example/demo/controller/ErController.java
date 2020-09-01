package com.example.demo.controller;

import com.example.demo.dto.request.CreateErRequest;
import com.example.demo.dto.request.UpdateErRequest;
import com.example.demo.dto.response.ErResponse;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.IErService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/er")
public class ErController {

    private IErService _erService;

    public ErController(IErService erService) {
        _erService = erService;
    }

    @GetMapping
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public List<ErResponse> getAllErs() { return _erService.getAllErs(); }

    @PostMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    ErResponse createEr(@PathVariable UUID id, @RequestBody CreateErRequest request) {
        try {
            return _erService.createEr(request, id);
        } catch (Exception ex) {
            return null;
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    ErResponse updateEr(@PathVariable UUID id, @RequestBody UpdateErRequest request) {
        try {
            return _erService.updateEr(id, request);
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public  ErResponse getEr(@PathVariable UUID id) {
        return _erService.getEr(id);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(AuthoritiesConstants.ADMIN_ROLE)
    public void deleteMEr(@PathVariable UUID id) {
        _erService.deleteEr(id);
    }

}
