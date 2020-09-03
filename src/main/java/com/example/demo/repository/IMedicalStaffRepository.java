package com.example.demo.repository;

import com.example.demo.entity.AppointmentType;
import com.example.demo.entity.Clinic;
import com.example.demo.entity.Grade;
import com.example.demo.entity.MedicalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IMedicalStaffRepository extends JpaRepository<MedicalStaff, UUID> {

    MedicalStaff findOneById(UUID id);

    List<MedicalStaff> findAllByClinic(Clinic clinic);

    List<MedicalStaff> findAllByAppointmentType(AppointmentType appointmentType);

    List<MedicalStaff> findAllByAppointmentTypeAndUser_IsDeleted(AppointmentType appointmentType, boolean isDeleted);

    List<MedicalStaff> findAllByAppointmentTypeAndClinic(AppointmentType appointmentType, Clinic clinic);

    List<MedicalStaff> findAllByClinicAndUser_IsDeleted(Clinic clinic, boolean isDeleted);

    List<MedicalStaff> findAllByUser_IsDeleted(boolean isDeleted);

    List<MedicalStaff> findAllByAppointmentTypeAndClinicAndUser_IsDeleted(AppointmentType appointmentType, Clinic clinic, boolean isDeleted);
}
