package com.example.demo.dto.response;

import com.example.demo.util.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestNewResponse {

    private UUID id;

    private Date appointmentDate;

    private String doctorName;

    private String appointmentTypeName;

    private String clinicName;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime localTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime localTime2;

    private RequestStatus status;

    private UUID clinicId;
}
