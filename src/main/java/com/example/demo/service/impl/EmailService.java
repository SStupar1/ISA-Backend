package com.example.demo.service.impl;

import com.example.demo.config.EmailContext;
import com.example.demo.entity.Patient;
import com.example.demo.entity.User;
import com.example.demo.service.IEmailService;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class EmailService implements IEmailService {

    private final EmailContext _emailContext;

    public EmailService(EmailContext emailContext) {
        _emailContext = emailContext;
    }

    @Override
    public void sendAcceptedMailToPatient(User user) {
        String to = user.getEmail();
        String subject = "Your registration has been approved!";
        Context context = new Context();
        context.setVariable("username", String.format("%s %s", user.getFirstName(), user.getLastName()));
        _emailContext.send(to, subject, "acceptedPatient", context);
    }

    @Override
    public void sendADeniedMailToPatient(User user) {
        String to = user.getEmail();
        String subject = "Your registration has been denied!";
        Context context = new Context();
        context.setVariable("username", String.format("%s %s", user.getFirstName(), user.getLastName()));
        _emailContext.send(to, subject, "deniedPatient", context);
    }

    @Override
    public void sendEmailToAdminForNewRequestAppoitments(List<String> clinicAdmins) {

        if (clinicAdmins.isEmpty())
            return;

        for (String email : clinicAdmins) {
            String to = email;
            String subject = "You have a new appointment request!";
            Context context = new Context();
            _emailContext.send(to, subject, "newRequestAppointment", context);
        }
    }

    @Override
    public void sendEmailToPatientForNewRequestAppointments(String patient_email) {

        if(patient_email.isEmpty())
            return;

        String subject = "You have a response to your appointment request!";
        Context context = new Context();
        _emailContext.send(patient_email, subject, "appointmentRequestResponse", context);

    }

    @Override
    public void sendNegativeEmailToPatientForNewRequestAppointments(String patient_email) {
        if(patient_email.isEmpty())
            return;

        String subject = "You have a response to your appointment request!";
        Context context = new Context();
        _emailContext.send(patient_email, subject, "appointmentRequestNegativeResponse", context);
    }

    @Override
    public void sendMailAboutVacation(User admin, User medical) {
        String to = admin.getEmail();
        String subject = "Vacation announcement!";
        Context context = new Context();
        context.setVariable("username", String.format("%s %s", medical.getFirstName(), medical.getLastName()));
        _emailContext.send(to, subject, "vacation", context);
    }

    @Override
    public void sendNotificationToPatient(User user) {
        String to = user.getEmail();
        String subject = "Predef appointment announcement!";
        Context context = new Context();
        context.setVariable("username", String.format("%s %s", user.getFirstName(), user.getLastName()));
        _emailContext.send(to, subject, "potentialappointment", context);
    }
}
