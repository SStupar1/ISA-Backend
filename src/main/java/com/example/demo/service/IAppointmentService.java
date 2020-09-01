package com.example.demo.service;

import com.example.demo.dto.request.AppointmentSearchRequest;
import com.example.demo.dto.request.DoctorSearchRequest;
import com.example.demo.dto.request.SuggestErRequest;
import com.example.demo.dto.response.AppointmentResponse;
import com.example.demo.dto.response.ClinicResponse;
import com.example.demo.dto.response.MedicalStaffResponse;
import com.example.demo.entity.MedicalStaff;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface IAppointmentService {

    List<AppointmentResponse> getAllAppointments();

    Set<ClinicResponse> getClinicsByTimeAndAppointemntType(AppointmentSearchRequest request);

    List<MedicalStaffResponse> getDoctorsByChosenClinic(DoctorSearchRequest request);

    void suggestEr(SuggestErRequest request);
}
