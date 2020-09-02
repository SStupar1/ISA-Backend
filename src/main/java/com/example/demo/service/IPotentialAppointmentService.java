package com.example.demo.service;

import com.example.demo.dto.request.CreateErAppointmentRequest;
import com.example.demo.dto.request.ScheduleAppointmentRequest;
import com.example.demo.dto.response.AppointmentRequestNewResponse;

import java.util.List;

public interface IPotentialAppointmentService {

    void createPotentialAppointmentByDoctor(CreateErAppointmentRequest request);

    List<AppointmentRequestNewResponse> getAllPotentialAppointmentRequest();

    void schedulePredefAppointment(ScheduleAppointmentRequest request);
}
