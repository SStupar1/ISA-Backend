package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentSearchRequest {

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date;

    private String startAt;

    private UUID appointmentTypeId;
}
