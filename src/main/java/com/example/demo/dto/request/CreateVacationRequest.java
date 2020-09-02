package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CreateVacationRequest {

    private UUID doctorId;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private List<Date> dates;
}
