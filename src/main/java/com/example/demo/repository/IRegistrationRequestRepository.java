package com.example.demo.repository;

import com.example.demo.entity.RegistrationRequest;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IRegistrationRequestRepository extends JpaRepository<RegistrationRequest, UUID> {
    RegistrationRequest findOneById(UUID id);
}
