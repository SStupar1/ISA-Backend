package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErAvailableRequest {

    private String startAt;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date;

    private UUID clinicId;
}
