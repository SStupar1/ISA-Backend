package com.example.demo.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ConfirmAppointmentRequest {
    UUID patientId;
}
