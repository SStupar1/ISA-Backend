package com.example.demo.dto.request;

import com.example.demo.entity.AppointmentType;
import com.example.demo.util.enums.MedicalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMedicalStaffRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String country;

    private String city;

    private String address;

    private String phone;

    private String ssn;

    private String password;

    //private String rePassword;

    private MedicalType medicalType;

    private LocalTime startWorkAt;

    private LocalTime endWorkAt;

    private UUID appointmentTypeId;

    private UUID clinicId;
}
