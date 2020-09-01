package com.example.demo.service;

import com.example.demo.dto.request.CreatePatientRequest;
import com.example.demo.dto.request.PatientFilterRequest;
import com.example.demo.dto.request.UpdatePatientRequest;
import com.example.demo.dto.response.*;
import com.example.demo.entity.ErAppointmentPeriod;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IPatientService {

    PatientResponse createPatient(CreatePatientRequest request) throws Exception;

    PatientResponse updatePatient(UUID id, UpdatePatientRequest request) throws Exception;

    PatientResponse getPatient(UUID id) throws Exception;

    List<PatientResponse> getPatients(PatientFilterRequest patientFilterRequest);

    List<PatientResponse> getAllPatientsByClinic(UUID id, PatientFilterRequest patientFilterRequest) throws Exception;

    void deletePatient(UUID id);

    List<AppointmentRequestNewResponse> getAllAppointmentRequestsByPatient(UUID id);

    List<ErAppointmentPeriodResponse> getAllAppointments(UUID id);

    Set<MedicalStaffResponse> getDoctorsByAppointments(UUID id);

    Set<ClinicResponse> getClinicsByAppointments(UUID id);

    Set<PatientResponse> getPatientsByAppointments(UUID id);
}
