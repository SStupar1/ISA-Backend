package com.example.demo.service;


import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response                                                                                                                                 .;;;;;;;;;;;;;;;;;;;;;;;;;;q   ADC.LoginResponse;

import java.util.UUID;

public interface IAuthService {

    LoginResponse login(LoginRequest request) throws Exception;

}
