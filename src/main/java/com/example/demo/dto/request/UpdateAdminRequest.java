package com.example.demo.dto.request;

import com.example.demo.util.enums.AdminType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdminRequest {
    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String address;

    private String phone;

    private String ssn;

    private AdminType adminType;
}
