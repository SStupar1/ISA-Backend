package com.example.demo.dto.response;


import com.example.demo.util.enums.AppointmentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {

    private UUID id;

    private float price;

    private AppointmentTypeEnum appType;
}
