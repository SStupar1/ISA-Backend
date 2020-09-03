package com.example.demo.dto.response;

import com.example.demo.entity.Clinic;
import com.example.demo.util.enums.MedicalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MedicalStaffResponse {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String country;

    private String city;

    private String address;

    private String phone;

    private String ssn;

    private MedicalType medicalType;

    private Clinic clinic;

    private LocalTime startWorkAt;

    private LocalTime endWorkAt;

    private UUID appointmentTypeId;
}
