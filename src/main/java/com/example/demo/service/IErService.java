package com.example.demo.service;

import com.example.demo.dto.request.CreateErRequest;
import com.example.demo.dto.request.ErFilterRequest;
import com.example.demo.dto.request.UpdateErRequest;
import com.example.demo.dto.response.ErResponse;

import java.util.List;
import java.util.UUID;

public interface IErService {

    List<ErResponse> getAllErs();

    List<ErResponse> getAllErsByClinic(UUID id) throws Exception;

    ErResponse updateEr(UUID id, UpdateErRequest request) throws Exception;

    ErResponse createEr(CreateErRequest request, UUID clinicId);

    ErResponse getEr(UUID id);

    void deleteEr(UUID id);

    List<ErResponse> getAllErsByClinic(UUID id, ErFilterRequest request);
}
