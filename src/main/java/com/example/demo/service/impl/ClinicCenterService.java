package com.example.demo.service.impl;

import com.example.demo.dto.request.CreateClinicCenterRequest;
import com.example.demo.dto.response.ClinicCenterResponse;
import com.example.demo.entity.ClinicCenter;
import com.example.demo.repository.IClinicCenterRepository;
import com.example.demo.service.IClinicCenterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClinicCenterService implements IClinicCenterService {

    private final PasswordEncoder _passwordEncoder;
    private  final IClinicCenterRepository _clinicCenterRepository;

    public ClinicCenterService(PasswordEncoder passwordEncoder, IClinicCenterRepository clinicCenterRepository) {
        _passwordEncoder = passwordEncoder;
        _clinicCenterRepository = clinicCenterRepository;
    }

    @Override
    public ClinicCenterResponse createClinicCenter(CreateClinicCenterRequest request) throws Exception {
        ClinicCenter clinicCenter = new ClinicCenter();
        ClinicCenter savedClinicCenter = _clinicCenterRepository.save(clinicCenter);
        return mapClinicCenterToClinicCenterResponse(savedClinicCenter);
    }

    private ClinicCenterResponse mapClinicCenterToClinicCenterResponse(ClinicCenter clinicCenter) {
        ClinicCenterResponse clinicCenterResponse = new ClinicCenterResponse();
        clinicCenterResponse.setId(clinicCenter.getId());
        return clinicCenterResponse;
    }
}
