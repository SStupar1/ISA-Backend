package com.example.demo.service.impl;

import com.example.demo.dto.request.CreateErAppointmentRequest;
import com.example.demo.dto.request.ScheduleAppointmentRequest;
import com.example.demo.dto.response.AppointmentRequestNewResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.IEmailService;
import com.example.demo.service.IPotentialAppointmentService;
import com.example.demo.util.enums.CalendarType;
import com.example.demo.util.enums.RequestStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PotentialAppointmentService implements IPotentialAppointmentService {

    private final IMedicalStaffRepository _medicalStaffRepository;

    private final IAppointmentRequestRepository _appointmentRequestRepository;

    private final IErRepository _erRepository;

    private final IPatientRepository _patientRepository;

    private final ICalendarRepository _calendarRepository;

    private final IErAppointmentPeriodRepository _erAppointmentPeriodRepository;

    private final IEmailService _emailService;

    public PotentialAppointmentService(IMedicalStaffRepository medicalStaffRepository, IAppointmentRequestRepository appointmentRequestRepository, IErRepository erRepository, IPatientRepository patientRepository, ICalendarRepository calendarRepository, IErAppointmentPeriodRepository erAppointmentPeriodRepository, IEmailService emailService) {
        _medicalStaffRepository = medicalStaffRepository;
        _appointmentRequestRepository = appointmentRequestRepository;
        _erRepository = erRepository;
        _patientRepository = patientRepository;
        _calendarRepository = calendarRepository;
        _erAppointmentPeriodRepository = erAppointmentPeriodRepository;
        _emailService = emailService;
    }

    @Override
    public void createPotentialAppointmentByDoctor(CreateErAppointmentRequest request) {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(request.getDoctorId());
        AppointmentType appointmentType = medicalStaff.getAppointmentType();
        Clinic clinic = medicalStaff.getClinic();
        AppointmentRequest appointmentRequest = new AppointmentRequest();
        appointmentRequest.setAppointmentType(appointmentType);
        appointmentRequest.setAppointmentDate(request.getDate());
        appointmentRequest.setEr(_erRepository.findOneById(request.getErId()));
        appointmentRequest.setClinic(clinic);
        appointmentRequest.setMedicalStaff(medicalStaff);
        String[] tokens = request.getStartAt().split(":");
        LocalTime localTime =  LocalTime.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));

        appointmentRequest.setStatus(RequestStatus.PREDEF);

        appointmentRequest.setStartAt(localTime);
        appointmentRequest.setEndAt(localTime.plusHours(1L));

        _appointmentRequestRepository.save(appointmentRequest);
    }

    public List<AppointmentRequestNewResponse> getAllPotentialAppointmentRequest() {
        List<AppointmentRequest> result = _appointmentRequestRepository.findAllByStatus(RequestStatus.PREDEF);

        return result.stream().map(this::mapAppointmentRequestToAppointmentRequestNewResponse).collect(Collectors.toList());

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void schedulePredefAppointment(ScheduleAppointmentRequest request) {
        Patient patient = _patientRepository.findOneById(request.getPatientId());
        AppointmentRequest appointmentRequest = _appointmentRequestRepository.findOneById(request.getPotentialAppointmentId());
        appointmentRequest.setStatus(RequestStatus.APPROVED);
        appointmentRequest.setPatient(patient);
        _appointmentRequestRepository.save(appointmentRequest);
        ErAppointmentPeriod erAppointmentPeriod = new ErAppointmentPeriod();
        erAppointmentPeriod.setEr(appointmentRequest.getEr());
        erAppointmentPeriod.setDate(appointmentRequest.getAppointmentDate());
        erAppointmentPeriod.setPrice(appointmentRequest.getAppointmentType().getPrice());
        erAppointmentPeriod.setEndAt(appointmentRequest.getEndAt());
        erAppointmentPeriod.setStartAt(appointmentRequest.getStartAt());
        Calendar calendar = new Calendar();
        calendar.setCalendarType(CalendarType.WORKING);
        calendar.setPatientId(request.getPatientId());
        calendar.setMedicalStaff(appointmentRequest.getMedicalStaff());
        calendar.setDate(appointmentRequest.getAppointmentDate());
        calendar.setStartAt(appointmentRequest.getStartAt());
        calendar.setEndAt(appointmentRequest.getEndAt());
        _calendarRepository.save(calendar);
        erAppointmentPeriod.setCalendar(calendar);
        _erAppointmentPeriodRepository.save(erAppointmentPeriod);
        MedicalStaff medicalStaff = appointmentRequest.getMedicalStaff();
        medicalStaff.getCalendars().add(calendar);
        _medicalStaffRepository.save(medicalStaff);

        _emailService.sendNotificationToPatient(patient.getUser());
    }


    private AppointmentRequestNewResponse mapAppointmentRequestToAppointmentRequestNewResponse(AppointmentRequest request) {
        AppointmentRequestNewResponse appointmentRequestNewResponse = new AppointmentRequestNewResponse();
        appointmentRequestNewResponse.setAppointmentDate(request.getAppointmentDate());
        appointmentRequestNewResponse.setAppointmentTypeName(request.getAppointmentType().getName());
        appointmentRequestNewResponse.setClinicName(request.getClinic().getName());
        appointmentRequestNewResponse.setDoctorName(request.getMedicalStaff().getUser().getFirstName());
        appointmentRequestNewResponse.setStatus(request.getStatus());
        appointmentRequestNewResponse.setLocalTime(request.getStartAt());
        appointmentRequestNewResponse.setClinicId(request.getClinic().getId());
        appointmentRequestNewResponse.setLocalTime2(request.getEndAt());
        appointmentRequestNewResponse.setId(request.getId());
        return appointmentRequestNewResponse;

    }
}
