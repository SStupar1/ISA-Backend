package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErAppointmentPeriodResponse {

    private Date date;

    private String doctorName;

    private String clinicName;

    private String appointmentTypeName;

    private String startAt;
}
