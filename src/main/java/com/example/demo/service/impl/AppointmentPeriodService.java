package com.example.demo.service.impl;

import com.example.demo.dto.response.AppointmentPeriodResponse;
import com.example.demo.entity.AppointmentPeriod;
import com.example.demo.repository.IAppointmentPeriodRepository;
import com.example.demo.service.IAppointmentPeriodService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentPeriodService implements IAppointmentPeriodService {

    private final IAppointmentPeriodRepository _appointmentPeriodRepository;

    public AppointmentPeriodService(IAppointmentPeriodRepository appointmentPeriodRepository) {
        _appointmentPeriodRepository = appointmentPeriodRepository;
    }

    @Override
    public List<AppointmentPeriodResponse> getAllAppointmentPeriods() {
        List<AppointmentPeriod> appointmentPeriods = _appointmentPeriodRepository.findAll();

        return appointmentPeriods.stream().map(appointmentPeriod -> mapAppointmentPeriodToAppointmentPeriodResponse(appointmentPeriod)).collect(Collectors.toList());

    }

    private AppointmentPeriodResponse mapAppointmentPeriodToAppointmentPeriodResponse(AppointmentPeriod appointmentPeriod) {
        AppointmentPeriodResponse appointmentPeriodResponse = new AppointmentPeriodResponse();
        appointmentPeriodResponse.setId(appointmentPeriod.getId());
        appointmentPeriodResponse.setStartD(appointmentPeriod.getStartD());
        appointmentPeriodResponse.setEndD(appointmentPeriod.getEndD());

        return appointmentPeriodResponse;
    }

}
