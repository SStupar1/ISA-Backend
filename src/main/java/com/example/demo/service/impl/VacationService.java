package com.example.demo.service.impl;

import com.example.demo.dto.request.CreateVacationRequest;
import com.example.demo.entity.Calendar;
import com.example.demo.entity.MedicalStaff;
import com.example.demo.repository.ICalendarRepository;
import com.example.demo.repository.IMedicalStaffRepository;
import com.example.demo.service.IEmailService;
import com.example.demo.service.IVacationService;
import com.example.demo.utils.enums.CalendarType;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;

@Service
public class VacationService implements IVacationService {

    private final IMedicalStaffRepository _medicalStaffRepository;

    private final ICalendarRepository _calendarRepository;

    private final IEmailService _emailService;

    public VacationService(IMedicalStaffRepository medicalStaffRepository, ICalendarRepository calendarRepository, IEmailService emailService) {
        _medicalStaffRepository = medicalStaffRepository;
        _calendarRepository = calendarRepository;
        _emailService = emailService;
    }

    @Override
    public void sendVacationRequest(CreateVacationRequest createVacationRequest) {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(createVacationRequest.getDoctorId());

        for(Date date: createVacationRequest.getDates()) {
            Calendar workCalendar = new Calendar();
            workCalendar.setMedicalStaff(medicalStaff);
            workCalendar.setStartAt(LocalTime.parse("00:00:00"));
            workCalendar.setEndAt(LocalTime.parse("23:59:59"));
            workCalendar.setDate(date);
            workCalendar.setCalendarType(CalendarType.ABSENT);
            _calendarRepository.save(workCalendar);
            medicalStaff.getCalendars().add(workCalendar);
        }
        _medicalStaffRepository.save(medicalStaff);

        _emailService.sendMailAboutVacation(medicalStaff.getClinic().getAdmins().get(0).getUser(), medicalStaff.getUser());
    }
}