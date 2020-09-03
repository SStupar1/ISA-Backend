package com.example.demo.service.impl;

import com.example.demo.dto.request.GradeClinicRequest;
import com.example.demo.dto.request.GradeDoctorRequest;
import com.example.demo.dto.response.AvgGradeResponse;
import com.example.demo.entity.Clinic;
import com.example.demo.entity.Grade;
import com.example.demo.entity.MedicalStaff;
import com.example.demo.repository.IClinicRepository;
import com.example.demo.repository.IMedicalStaffRepository;
import com.example.demo.service.IAddGradeService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddGradeService implements IAddGradeService {

    private final IMedicalStaffRepository _medicalStaffRepository;

    private final IClinicRepository _clinicRepository;

    public AddGradeService(IMedicalStaffRepository medicalStaffRepository, IClinicRepository clinicRepository) {
        _medicalStaffRepository = medicalStaffRepository;
        _clinicRepository = clinicRepository;
    }

    @Override
    public void addGrade(GradeDoctorRequest request) throws Exception{
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(request.getDoctorId());
        Grade grade = new Grade();
        grade.setGrade(Integer.parseInt(request.getGrade()));
        grade.setPatientId(request.getPatientId());

        for(Grade g: medicalStaff.getGrades()){
            if(grade.getPatientId().equals(request.getPatientId())){
                throw new Exception("Already graded.");
            }
        }

        medicalStaff.getGrades().add(grade);
        _medicalStaffRepository.save(medicalStaff);
    }

    @Override
    public void addGrade(GradeClinicRequest request) throws Exception{
        Clinic clinic = _clinicRepository.findOneById(request.getClinicId());
        Grade grade = new Grade();
        grade.setGrade(Integer.parseInt(request.getGrade()));
        grade.setPatientId(request.getPatientId());

        for(Grade g: clinic.getGrades()){
            if(grade.getPatientId().equals(request.getPatientId())){
                throw new Exception("Already graded.");
            }
        }

        clinic.getGrades().add(grade);
        _clinicRepository.save(clinic);
    }

    @Override
    public AvgGradeResponse getDoctorsAvgGrade(UUID id) {
        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);
        float sum = 0;
        for(Grade g: medicalStaff.getGrades()){
            sum += g.getGrade();
        }
        float avg = 0;
        if(sum != 0) {
            avg = sum/medicalStaff.getGrades().size();
        }
        AvgGradeResponse response = new AvgGradeResponse();
        response.setAvgGrade(String.valueOf(avg));
        return response;
    }

    @Override
    public AvgGradeResponse getClinicsAvgGrade(UUID id) {
        Clinic clinic = _clinicRepository.findOneById(id);
        float sum = 0;
        for(Grade g: clinic.getGrades()){
            sum += g.getGrade();
        }
        float avg = 0;
        if(sum != 0){
            avg = sum/clinic.getGrades().size();
        }
        AvgGradeResponse response = new AvgGradeResponse();
        response.setAvgGrade(String.valueOf(avg));
        return response;
    }
}
