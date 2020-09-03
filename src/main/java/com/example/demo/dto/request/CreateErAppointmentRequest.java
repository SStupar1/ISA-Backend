package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CreateErAppointmentRequest {

    private UUID erId;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date;

    private String startAt;

    private UUID doctorId;
}
