package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CreateAppointmentRequestAsDoctorRequest {

    private UUID doctorId;

    private  String patientEmail;

    private String startAt;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date;

    private String currentTime;
}
