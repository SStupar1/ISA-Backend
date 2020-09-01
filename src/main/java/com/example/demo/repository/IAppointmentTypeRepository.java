package com.example.demo.repository;

import com.example.demo.entity.AppointmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAppointmentTypeRepository extends JpaRepository<AppointmentType, UUID> {
    AppointmentType findOneById(UUID id);
    List<AppointmentType> findAllByIsDeleted(boolean isDeletd);
}
