package com.example.demo.service;

import com.example.demo.dto.response.AppointmentPeriodResponse;

import java.util.List;

public interface IAppointmentPeriodService {

    List<AppointmentPeriodResponse> getAllAppointmentPeriods();
}