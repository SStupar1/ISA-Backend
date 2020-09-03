package com.example.demo.repository;

import com.example.demo.entity.AppointmentRequest;
import com.example.demo.entity.Patient;
import com.example.demo.util.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAppointmentRequestRepository extends JpaRepository<AppointmentRequest, UUID> {

    AppointmentRequest findOneById(UUID id);
    List<AppointmentRequest> findAllByPatient_Id(UUID id);
    List<AppointmentRequest> findAllByClinic_Id(UUID id);
    List<AppointmentRequest> findAllByStatus(RequestStatus status);
    List<AppointmentRequest> findAllByPatient(Patient patient);
    List<AppointmentRequest> findAllByPatientAndStatus(Patient patient, RequestStatus status);
}
