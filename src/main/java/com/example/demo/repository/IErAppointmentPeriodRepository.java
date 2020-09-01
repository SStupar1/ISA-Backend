package com.example.demo.repository;

import com.example.demo.entity.ErAppointmentPeriod;
import com.example.demo.entity.MedicalStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface IErAppointmentPeriodRepository extends JpaRepository<ErAppointmentPeriod, UUID> {

    List<ErAppointmentPeriod> findAllByStartAtGreaterThanAndEndAtBefore(Date startAt, Date endAt);

    List<ErAppointmentPeriod> findAllByCalendar_PatientId(UUID id);

    ErAppointmentPeriod findOneByCalendar_MedicalStaff(MedicalStaff medicalStaff);
}