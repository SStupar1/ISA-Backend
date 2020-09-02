package com.example.demo.service.impl;

import com.example.demo.dto.request.GradeClinicRequest;
import com.example.demo.dto.request.GradeDoctorRequest;
import com.example.demo.entity.Clinic;
import com.example.demo.entity.Grade;
import com.example.demo.entity.MedicalStaff;
import com.example.demo.repository.IClinicRepository;
import com.example.demo.repository.IMedicalStaffRepository;
import com.example.demo.service.IAddGradeService;
import org.springframework.stereotype.Service;

@Service
public class AddGradeService implements IAddGradeService {

    private final IMedicalStaffRepository _medicalStaffRepository;

    private final IClinicRepository _clinicRepository;

    public AddGradeService(IMedicalStaffRepository medicalStaffRepository, IClinicRepository clinicRepository) {
        _medicalStaffRepository = medicalStaffRepository;
        _clinicRepository = clinicRepository;
    }

    @Override
    public void addGrade(GradeDoctorRequest request) {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(request.getDoctorId());
        Grade grade = new Grade();
        grade.setGrade(Integer.parseInt(request.getGrade()));
        grade.setPatientId(request.getPatientId());

        for(Grade g: medicalStaff.getGrades()){
            if(grade.getPatientId().equals(request.getPatientId())){
                return;
            }
        }

        medicalStaff.getGrades().add(grade);
        _medicalStaffRepository.save(medicalStaff);
    }

    @Override
    public void addGrade(GradeClinicRequest request) {
        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());
        Grade grade = new Grade();
        grade.setGrade(Integer.parseInt(request.getGrade()));
        grade.setPatientId(request.getPatientId());

        for(Grade g: clinic.getGrades()){
            if(grade.getPatientId().equals(request.getPatientId())){
                return;
            }
        }

        clinic.getGrades().add(grade);
        _clinicRepository.save(clinic);
    }
}