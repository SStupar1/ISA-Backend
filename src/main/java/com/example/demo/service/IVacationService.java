package com.example.demo.service;

import com.example.demo.dto.request.CreateVacationRequest;

public interface IVacationService {

    void sendVacationRequest(CreateVacationRequest request);
}
