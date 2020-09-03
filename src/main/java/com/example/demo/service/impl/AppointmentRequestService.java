package com.example.demo.service.impl;

import com.example.demo.dto.request.ConfirmAppointmentRequest;
import com.example.demo.dto.request.CreateAppointmentRequestAsDoctorRequest;
import com.example.demo.dto.request.CreateAppointmentRequestRequest;
import com.example.demo.dto.request.ErAvailableRequest;
import com.example.demo.dto.response.AppointmentRequestNewResponse;
import com.example.demo.dto.response.AppointmentRequestResponse;
import com.example.demo.dto.response.ErResponse;
import com.example.demo.entity.*;
import com.example.demo.repository.*;
import com.example.demo.service.IAppointmentRequestService;
import com.example.demo.service.IEmailService;
import com.example.demo.util.enums.CalendarType;
import com.example.demo.util.enums.RequestStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentRequestService implements IAppointmentRequestService {

    private final IAppointmentRequestRepository _appointmentRequestRepository;

    private final IMedicalStaffRepository _medicalStaffRepository;

    private final IPatientRepository _patientRepository;

    private final IAppointmentTypeRepository _appointmentTypeRepository;

    private final IClinicRepository _clinicRepository;

    private final IEmailService _emailService;

    private  final IErRepository _erRepository;

    private final ICalendarRepository _calendarRepository;

    private final IErAppointmentPeriodRepository _erAppointmentRepository;



    public AppointmentRequestService(IAppointmentRequestRepository appintmentRequestRepository, IMedicalStaffRepository medicalStaffRepository, IPatientRepository patientRepository, IAppointmentTypeRepository appointmentTypeRepository, IClinicRepository clinicRepository, IEmailService emailService, IErRepository erRepository, ICalendarRepository calendarRepository, IErAppointmentPeriodRepository erAppointmentRepository) {
        _appointmentRequestRepository = appintmentRequestRepository;
        _medicalStaffRepository = medicalStaffRepository;
        _patientRepository = patientRepository;
        _appointmentTypeRepository = appointmentTypeRepository;
        _clinicRepository = clinicRepository;
        _emailService = emailService;
        _erRepository = erRepository;
        _calendarRepository = calendarRepository;
        _erAppointmentRepository = erAppointmentRepository;
    }


    @Override
    public List<AppointmentRequestResponse> getAll() {
        List<AppointmentRequest> appointmentRequests = _appointmentRequestRepository.findAll();

        return appointmentRequests
                .stream()
                .map(a -> mapAppointmentRequestToAppointmentRequestResponse(a))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentRequestNewResponse> getAllByClinic(UUID id) {
        List<AppointmentRequest> appointmentRequests = _appointmentRequestRepository.findAllByClinic_Id(id);

        return appointmentRequests
                .stream()
                .map(a -> mapAppointmentRequestToAppointmentRequestNewResponse(a))
                .collect(Collectors.toList());
    }

    @Override
    public void approveAppointmentRequest(UUID id, ConfirmAppointmentRequest confirmAppointmentRequest) throws Exception {
        AppointmentRequest request = _appointmentRequestRepository.findOneById(id);

        if (request == null) {
            throw new Exception(String.format("Request with %s id not found", id.toString()));
        }

        request.setStatus(RequestStatus.APPROVED);
        _appointmentRequestRepository.save(request);

        Calendar calendar = new Calendar();
        calendar.setDate(request.getAppointmentDate());
        calendar.setStartAt(request.getStartAt());
        calendar.setEndAt(request.getEndAt());
        calendar.setMedicalStaff(request.getMedicalStaff());
        calendar.setCalendarType(CalendarType.WORKING);

        calendar.setPatientId(confirmAppointmentRequest.getPatientId());

        _calendarRepository.save(calendar);

        ErAppointmentPeriod erAppointmentPeriod = new ErAppointmentPeriod();
        erAppointmentPeriod.setCalendar(calendar);
        erAppointmentPeriod.setDate(request.getAppointmentDate());
        erAppointmentPeriod.setStartAt(request.getStartAt());
        erAppointmentPeriod.setEndAt(request.getEndAt());
        erAppointmentPeriod.setEr(request.getEr());
        erAppointmentPeriod.setPrice(request.getAppointmentType().getPrice());



        _erAppointmentRepository.save(erAppointmentPeriod);
    }

    @Override
    public void denyAppointmentRequest(UUID id) throws Exception {
        AppointmentRequest request = _appointmentRequestRepository.findOneById(id);

        if (request == null) {
            throw new Exception(String.format("Request with %s id not found", id.toString()));
        }

        request.setStatus(RequestStatus.DENIED);
        _appointmentRequestRepository.save(request);
    }

    @Override
    public void createAppointmentRequest(CreateAppointmentRequestRequest request) {

        AppointmentRequest appointmentRequest = mapToAppointmentRequest(request);
        appointmentRequest.setPatient(_patientRepository.findOneById(request.getPatientId()));
        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());
        appointmentRequest.setClinic(clinic);

        _appointmentRequestRepository.save(appointmentRequest);

        List<Admin> clinicAdmins = clinic.getAdmins();

        _emailService.sendEmailToAdminForNewRequestAppoitments(clinicAdmins.stream()
                .map(admin -> admin.getUser().getEmail())
                .collect(Collectors.toList()));
    }

    @Override
    public AppointmentRequestNewResponse getAppointmentRequest(UUID id) {
        AppointmentRequest appointmentRequest =_appointmentRequestRepository.findOneById(id);

        return mapAppointmentRequestToAppointmentRequestNewResponse(appointmentRequest);
    }

    @Override
    public List<ErResponse> getAvailableErs(ErAvailableRequest request) {
        List<Er> ers = _erRepository.findAllByClinic_IdAndIsDeleted(request.getClinicId(), false);

        List<Er> result = ers.stream()
                .filter(er -> {
                    List<ErAppointmentPeriod> erAppointmentPeriods = er.getErAppointmentPeriods();
                    if(erAppointmentPeriods.isEmpty()) {
                        return true;
                    }

                    if(!erAppointmentPeriods.stream().anyMatch(x -> x.getDate().getYear() == request.getDate().getYear()
                        && x.getDate().getMonth() == request.getDate().getMonth()
                        && x.getDate().getDay() == request.getDate().getDay())) {
                        return true;
                    }

                    String[] tokens = request.getStartAt().split(":");
                    LocalTime localTime =  LocalTime.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));

                    if(!erAppointmentPeriods.stream()
                        .filter(x -> x.getDate().getYear() == request.getDate().getYear() && x.getDate().getMonth() == request.getDate().getMonth() && x.getDate().getDay() == request.getDate().getDay())
                        .anyMatch(x -> x.getStartAt().isBefore(localTime) && x.getEndAt().isAfter(localTime))) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());

        return result.stream().map(this::mapErToErResponse).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void createAppointmentRequestByAdmin(UUID id_request, UUID id_er) {
        AppointmentRequest appointmentRequest = _appointmentRequestRepository.findOneById(id_request);
        appointmentRequest.setStatus(RequestStatus.CONFIRMED);
        appointmentRequest.setEndAt(appointmentRequest.getStartAt().plusHours(1L));
        appointmentRequest.setEr(_erRepository.findOneById(id_er));
        _appointmentRequestRepository.save(appointmentRequest);

        Patient patient = _patientRepository.findOneById(appointmentRequest.getPatient().getId());

        _emailService.sendEmailToPatientForNewRequestAppointments(patient.getUser().getEmail());

    }

    @Override
    public void denyAppointmentRequestByAdmin(UUID id_request) {
        AppointmentRequest appointmentRequest = _appointmentRequestRepository.findOneById(id_request);
        appointmentRequest.setStatus(RequestStatus.DENIED);
        _appointmentRequestRepository.save(appointmentRequest);

        Patient patient = _patientRepository.findOneById(appointmentRequest.getPatient().getId());

        _emailService.sendNegativeEmailToPatientForNewRequestAppointments(patient.getUser().getEmail());
    }

    @Override
    public void CreateAppointmentRequestAsDoctor(CreateAppointmentRequestAsDoctorRequest request) throws Exception {
        Patient patient = _patientRepository.findOneByUser_Email(request.getPatientEmail());
//        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());
//        appointmentRequest.setClinic(clinic);

        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(request.getDoctorId());
        Clinic clinic = medicalStaff.getClinic();
        Date now = new Date();
        String[] tokens = request.getCurrentTime().split(":");
        LocalTime currentTime =  LocalTime.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));

       boolean isTrue =  medicalStaff.getCalendars().stream()
                .anyMatch(calendar -> calendar.getDate().getYear() == now.getYear()
                && calendar.getDate().getMonth() == now.getMonth()
                && calendar.getDate().getDay() == now.getDay()
                && calendar.getStartAt().isBefore(currentTime)
                && calendar.getEndAt().isAfter(currentTime)
                && calendar.getPatientId().equals(patient.getId()));

        if(!isTrue) {
            throw new Exception("Pogresan pacijent");
        }

        AppointmentRequest appointmentRequest = mapToAppointmentRequest(request);
        appointmentRequest.setPatient(patient);
        appointmentRequest.setClinic(clinic);
        appointmentRequest.setAppointmentType(medicalStaff.getAppointmentType());
//        appointmentRequest.setEndAt();
        _appointmentRequestRepository.save(appointmentRequest);

        List<Admin> clinicAdmins = appointmentRequest.getClinic().getAdmins();

        _emailService.sendEmailToAdminForNewRequestAppoitments(clinicAdmins.stream()
                .map(admin -> admin.getUser().getEmail())
                .collect(Collectors.toList()));
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

    private AppointmentRequestResponse mapAppointmentRequestToAppointmentRequestResponse(AppointmentRequest appointmentRequest) {
        AppointmentRequestResponse appointmentRequestResponse = new AppointmentRequestResponse();
        appointmentRequestResponse.setId(appointmentRequest.getId());
        appointmentRequestResponse.setAppointmentDate(appointmentRequest.getAppointmentDate());
        appointmentRequestResponse.setStatus(appointmentRequest.getStatus());

        return appointmentRequestResponse;
    }

    private AppointmentRequest mapToAppointmentRequest(CreateAppointmentRequestRequest request) {
        AppointmentRequest result = new AppointmentRequest();
        result.setAppointmentDate(request.getAppointmentDate());
        result.setMedicalStaff(_medicalStaffRepository.findOneById(request.getDoctorId()));
        result.setClinic(_clinicRepository.findOneById(request.getClinicId()));
        result.setAppointmentType(_appointmentTypeRepository.findOneById(request.getAppointmentTypeId()));
        result.setStatus(RequestStatus.PENDING);

        String[] tokens = request.getStartAt().split(":");
        LocalTime localTime =  LocalTime.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
        result.setStartAt(localTime);


        return result;
    }

    private AppointmentRequest mapToAppointmentRequest(CreateAppointmentRequestAsDoctorRequest request) {
        AppointmentRequest result = new AppointmentRequest();
        result.setAppointmentDate(request.getDate());
        result.setMedicalStaff(_medicalStaffRepository.findOneById(request.getDoctorId()));
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(request.getDoctorId());
//        result.setPatient(_patientRepository.findOneByUser_Email(request.getPatientEmail()));
        result.setAppointmentType(_appointmentTypeRepository.findOneById(medicalStaff.getId()));
        result.setStatus(RequestStatus.PENDING);
        _appointmentRequestRepository.save(result);
        String[] tokens = request.getStartAt().split(":");
        LocalTime localTime =  LocalTime.of(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
        result.setStartAt(localTime);


        return result;
    }

    private ErResponse mapErToErResponse(Er er) {
        ErResponse erResponse = new ErResponse();
        erResponse.setId(er.getId());
        erResponse.setName(er.getName());
        erResponse.setNumber(er.getNumber());

        return erResponse;
    }
}
