package com.example.demo.service;

import com.example.demo.dto.request.FirstLoginPasswordRequest;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.UpdatePasswordRequest;
import com.example.demo.dto.response.LoginResponse;

import java.util.UUID;

public interface IAuthService {

    LoginResponse login(LoginRequest request) throws Exception;

    LoginResponse setNewPasswordOnFirstLoginMedical(UUID id, FirstLoginPasswordRequest request) throws Exception;

    LoginResponse setNewPasswordOnFirstLoginAdmin(UUID id, FirstLoginPasswordRequest request) throws Exception;

    void updatePasswordMedicalStaff(UUID id, UpdatePasswordRequest request) throws Exception;

    void updatePasswordAdmin(UUID id, UpdatePasswordRequest request) throws Exception;
}
