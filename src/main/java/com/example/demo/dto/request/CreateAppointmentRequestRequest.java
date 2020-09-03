package com.example.demo.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAppointmentRequestRequest {

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date appointmentDate;

    private UUID doctorId;

    private UUID appointmentTypeId;

    private UUID clinicId;

    private UUID patientId;

    private String startAt;
}
