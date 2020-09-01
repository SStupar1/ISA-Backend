package com.example.demo.service;

import com.example.demo.dto.request.CreateAdminRequest;
import com.example.demo.dto.request.UpdateAdminRequest;
import com.example.demo.dto.response.AdminResponse;
import java.util.List;
import java.util.UUID;

public interface IAdminService {
    AdminResponse createAdmin(CreateAdminRequest request) throws Exception;

    AdminResponse updateAdmin(UUID id, UpdateAdminRequest request) throws Exception;

    AdminResponse getAdmin(UUID id) throws Exception;

    List<AdminResponse> getAdmins();

    void deleteAdmin(UUID id);
}
