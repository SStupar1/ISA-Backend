package com.example.demo.service;

import com.example.demo.dto.request.CreateClinicCenterRequest;
import com.example.demo.dto.response.ClinicCenterResponse;

public interface IClinicCenterService {
    
    ClinicCenterResponse createClinicCenter(CreateClinicCenterRequest request) throws Exception;
}
