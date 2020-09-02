package com.example.demo.repository;

import com.example.demo.entity.AppointmentPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAppointmentPeriodRepository extends JpaRepository<AppointmentPeriod, UUID> {
    AppointmentPeriod findOneById(UUID id);
}