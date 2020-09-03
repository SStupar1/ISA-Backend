package com.example.demo.dto.response;

import com.example.demo.util.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequestResponse {

    private UUID id;

    private Date appointmentDate;

    private UUID doctorId;

    private UUID appointmentTypeId;

    private UUID clinicId;

    private RequestStatus status;
}
