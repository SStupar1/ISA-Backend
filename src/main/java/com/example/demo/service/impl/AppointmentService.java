package com.example.demo.service.impl;


import com.example.demo.dto.request.AppointmentSearchRequest;
import com.example.demo.dto.request.DoctorSearchRequest;
import com.example.demo.dto.request.ErAvailableRequest;
import com.example.demo.dto.request.SuggestErRequest;
import com.example.demo.dto.response.AppointmentResponse;
import com.example.demo.dto.response.ClinicResponse;
import com.example.demo.dto.response.ErResponse;
import com.example.demo.dto.response.MedicalStaffResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.IAppointmentRequestService;
import com.example.demo.service.IAppointmentService;
import com.example.demo.utils.enums.CalendarType;
import com.example.demo.utils.enums.MedicalType;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AppointmentService implements IAppointmentService {

    private final IAppointmentRepository _iAppointmentRepository;

    private final IClinicRepository _clinicRepository;

    private final IErAppointmentPeriodRepository _erAppointmentPeriodRepository;

    private final ICalendarRepository _calendarRepository;

    private final IMedicalStaffRepository _medicalStaffRepository;

    private final IErRepository _erRepository;

    private final IAppointmentRequestService _service;

    private final IAppointmentRequestRepository _appointmentRequestRepository;

    public AppointmentService(IAppointmentRepository iAppointmentRepository, IClinicRepository clinicRepository, IErAppointmentPeriodRepository erAppointmentPeriodRepository, ICalendarRepository calendarRepository, IMedicalStaffRepository medicalStaffRepository, IErRepository erRepository, IAppointmentRequestService service, IAppointmentRequestRepository appointmentRequestRepository) {
        _iAppointmentRepository = iAppointmentRepository;
        _clinicRepository = clinicRepository;
        _erAppointmentPeriodRepository = erAppointmentPeriodRepository;
        _calendarRepository = calendarRepository;
        _medicalStaffRepository = medicalStaffRepository;
        _erRepository = erRepository;
        _service = service;
        _appointmentRequestRepository = appointmentRequestRepository;
    }

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        List<Appointment> appointments = _iAppointmentRepository.findAll();

        return appointments.stream().map(appointment -> mapAppointmentToAppointmentResponse(appointment)).collect(Collectors.toList());
    }

    @Override
    public Set<ClinicResponse> getClinicsByTimeAndAppointemntType(AppointmentSearchRequest request) {
        String[] tokens = request.getStartAt().split(":");
        LocalTime localTime =  LocalTime.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
        AppointmentType appointmentType = new AppointmentType();

        appointmentType.setId(request.getAppointmentTypeId());

        List<MedicalStaff> medicalStaffs = _medicalStaffRepository.findAllByAppointmentTypeAndUser_IsDeleted(appointmentType, false);

        List<MedicalStaff> freeMedical = medicalStaffs.stream()
                .filter(medicalStaff -> medicalStaff.getMedicalType().equals(MedicalType.DOCTOR))
                .filter(medicalStaff -> medicalStaff.getStartWorkAt().isBefore(localTime) && medicalStaff.getEndWorkAt().isAfter(localTime))
                .filter(medicalStaff -> {
                    if(medicalStaff.getCalendars().isEmpty()) {
                        return true;
                    }
                    List<Calendar> calendars = _calendarRepository.findAllByMedicalStaff(medicalStaff);
                    if(calendars.isEmpty()) {
                        return true;
                    }
                    List<Calendar> unavailablePeriods = calendars.stream().filter(calendar ->
                            calendar.getStartAt().isBefore(localTime)
                                    && calendar.getEndAt().isAfter(localTime)
                                    && calendar.getCalendarType().equals(CalendarType.WORKING)
//                            && calendar.getDate().equals(request.getDate()))
                                    && calendar.getDate().getYear() == request.getDate().getYear()
                                    && calendar.getDate().getMonth() == request.getDate().getMonth()
                                    && calendar.getDate().getDay() == request.getDate().getDay())
                            .collect(Collectors.toList());
                    if(!unavailablePeriods.isEmpty() || calendars.stream().anyMatch(calendar -> calendar.getDate().getYear() == request.getDate().getYear()
                            && calendar.getDate().getMonth() == request.getDate().getMonth()
                            && calendar.getDate().getDay() == request.getDate().getDay()
                            && calendar.getCalendarType().equals(CalendarType.ABSENT))) {
                        return false;
                    } else {
                        return true;
                    }
                }).collect(Collectors.toList());

        Set<Clinic> freeClinics = freeMedical.stream().map(medicalStaff -> medicalStaff.getClinic()).collect(Collectors.toSet());
        return freeClinics.stream().map(this::mapClinicToClinicResponse).collect(Collectors.toSet());
    }

    @Override
    public List<MedicalStaffResponse> getDoctorsByChosenClinic(DoctorSearchRequest request) {
        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());
//        List<MedicalStaff> medicalStaffs = _medicalStaffRepository.findAllByClinic(clinic);

        String[] tokens = request.getStartAt().split(":");
        LocalTime localTime =  LocalTime.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
        AppointmentType appointmentType = new AppointmentType();
        appointmentType.setId(request.getAppointmentTypeId());

        List<MedicalStaff> medicalStaffs = _medicalStaffRepository.findAllByAppointmentTypeAndClinicAndUser_IsDeleted(appointmentType, clinic, false);

        List<MedicalStaff> availableDoctors = medicalStaffs.stream()
                .filter(medicalStaff -> medicalStaff.getMedicalType().equals(MedicalType.DOCTOR))
                .filter(medicalStaff -> medicalStaff.getStartWorkAt().isBefore(localTime) && medicalStaff.getEndWorkAt().isAfter(localTime))
                .filter(medicalStaff -> {
                    List<Calendar> calendars = _calendarRepository.findAllByMedicalStaff(medicalStaff);
                    if(calendars.isEmpty()) {
                        return true;
                    }
                    List<Calendar> unavailablePeriods = calendars.stream().filter(calendar ->
                            calendar.getStartAt().isBefore(localTime)
                                    && calendar.getEndAt().isAfter(localTime)
                                    && calendar.getCalendarType().equals(CalendarType.WORKING)
                                    && calendar.getDate().getYear() == request.getDate().getYear()
                                    && calendar.getDate().getMonth() == request.getDate().getMonth()
                                    && calendar.getDate().getDay() == request.getDate().getDay())
                            .collect(Collectors.toList());
                    if(!unavailablePeriods.isEmpty() || calendars.stream().anyMatch(calendar -> calendar.getDate().getYear() == request.getDate().getYear()
                            && calendar.getDate().getDay() == request.getDate().getDay() && calendar.getDate().getMonth() == request.getDate().getMonth()
                            && calendar.getCalendarType().equals(CalendarType.ABSENT))) {
                        return false;
                    } else {
                        return true;
                    }
                }).collect(Collectors.toList());

        return availableDoctors.stream().map(this::mapMedicalToMedicalResponse).collect(Collectors.toList());
    }

    @Override
    public void suggestEr(SuggestErRequest request) {
        AppointmentRequest request1 = _appointmentRequestRepository.findOneById(request.getId());
        MedicalStaff doctor = _medicalStaffRepository.findOneById(request1.getMedicalStaff().getId());
        Date now  = new Date();
        String startAt = "";
        boolean isFound = false;
        List<ErResponse> ers = new ArrayList<>();
        while (!isFound) {
            List<Calendar> calendars = doctor.getCalendars();
            if (calendars.isEmpty()) {
                startAt = doctor.getStartWorkAt().toString();
                ers = _service.getAvailableErs(ErAvailableRequest.builder().clinicId(doctor.getClinic().getId()).date(now).startAt(startAt).build());
                if (!ers.isEmpty()) {
                    isFound = true;
                }
            }
            List<Calendar> calendarForDay = new ArrayList<>();
            for (Calendar x : calendars) {
                if (x.getCalendarType().equals(CalendarType.WORKING)
                        && x.getDate().getYear() == now.getYear()
                        && x.getDate().getMonth() == now.getMonth()
                        && x.getDate().getDay() == now.getDay()) {
                    calendarForDay.add(x);
                }
            }
//            List<Calendar> calendarForDay = calendars.stream().filter(x ->  x.getCalendarType().equals(CalendarType.WORKING)
//                    && x.getDate().getYear() == now.getYear()
//                    && x.getDate().getMonth() == now.getMonth()
//                    && x.getDate().getDay() == now.getDay()).collect(Collectors.toList());
            if (calendarForDay.isEmpty()) {
                startAt = doctor.getStartWorkAt().toString();
                ers = _service.getAvailableErs(ErAvailableRequest.builder().clinicId(doctor.getClinic().getId()).date(now).startAt(startAt).build());
                if (!ers.isEmpty()) {
                    isFound = true;
                }
            } else {
                Calendar calendar = calendarForDay.get(calendarForDay.size() - 1);
                startAt = calendar.getEndAt().plusHours(1L).toString();
                ers = _service.getAvailableErs(ErAvailableRequest.builder().clinicId(doctor.getClinic().getId()).date(now).startAt(startAt).build());
                if (!ers.isEmpty()) {
                    isFound = true;
                }
            }
            // convert date to localdatetime
            LocalDateTime localDateTime = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            // plus one
            localDateTime = localDateTime.plusYears(1).plusMonths(1).plusDays(1);
            localDateTime = localDateTime.plusHours(1).plusMinutes(2).minusMinutes(1).plusSeconds(1);

            // convert LocalDateTime to date
            now = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        ErResponse er = ers.get(0);
        _service.createAppointmentRequestByAdmin(request.getId(), er.getId());
    }

    public AppointmentResponse mapAppointmentToAppointmentResponse(Appointment appointment) {
        AppointmentResponse appointmentResponse = new AppointmentResponse();

        appointmentResponse.setId(appointment.getId());
        appointmentResponse.setPrice(appointment.getPrice());
        appointmentResponse.setAppType(appointment.getAppType());

        return appointmentResponse;
    }

    public ClinicResponse mapClinicToClinicResponse(Clinic clinic) {
        ClinicResponse clinicResponse = new ClinicResponse();

        clinicResponse.setId(clinic.getId());
        clinicResponse.setName(clinic.getName());
        clinicResponse.setAddress(clinic.getAddress());
        clinicResponse.setDescription(clinic.getDescription());

        return clinicResponse;
    }

    private MedicalStaffResponse mapMedicalToMedicalResponse(MedicalStaff medicalStaff) {
        MedicalStaffResponse medicalStaffResponse = new MedicalStaffResponse();
        User user = medicalStaff.getUser();
        medicalStaffResponse.setAddress(user.getAddress());
        medicalStaffResponse.setCity(user.getCity());
        medicalStaffResponse.setCountry(user.getCountry());
        medicalStaffResponse.setEmail(user.getEmail());
        medicalStaffResponse.setFirstName(user.getFirstName());
        medicalStaffResponse.setLastName(user.getLastName());
        medicalStaffResponse.setId(medicalStaff.getId());
        medicalStaffResponse.setPhone(user.getPhone());
        medicalStaffResponse.setSsn(user.getSsn());
        medicalStaffResponse.setMedicalType(medicalStaff.getMedicalType());
//        medicalStaffResponse.setClinic(medicalStaff.getClinic());
//        medicalStaffResponse.setStartWorkAt(medicalStaff.getStartWorkAt());
//        medicalStaffResponse.setEndWorkAt(medicalStaff.getEndWorkAt());


        return medicalStaffResponse;
    }
}
