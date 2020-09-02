package com.example.demo.service;

import com.example.demo.entity.Patient;
import com.example.demo.entity.User;

import java.util.List;

public interface IEmailService {

    void sendAcceptedMailToPatient(User user);

    void sendADeniedMailToPatient(User user);

    void sendEmailToAdminForNewRequestAppoitments(List<String> clinicAdmins);

    void sendEmailToPatientForNewRequestAppointments(String patient_email);

    void sendNegativeEmailToPatientForNewRequestAppointments(String patient_email);

    void sendMailAboutVacation(User admin, User medical);

    void sendNotificationToPatient(User user);
}