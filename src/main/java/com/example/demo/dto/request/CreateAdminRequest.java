package com.example.demo.dto.request;

import com.example.demo.util.enums.AdminType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminRequest {
    private String firstName;

    private String lastName;

    private String email;

    private String country;

    private String city;

    private String address;

    private String phone;

    private String ssn;

    private String password;

    private String rePassword;

    //private AdminType adminType;

    private UUID clinicId;
}
