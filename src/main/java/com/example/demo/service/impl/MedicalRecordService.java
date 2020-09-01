package com.example.demo.service.impl;

import com.example.demo.entity.MedicalRecord;
import com.example.demo.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public MedicalRecord findByPatientId(UUID id) {return medicalRecordRepository.findOneByPatientId(id);}
    public MedicalRecord findOneById(Long id) {return medicalRecordRepository.findOneById(id);}
    public int updateMedicalRecord(double height,double weight,double diopter,String alergies,String bloodType,Long id) {
        return medicalRecordRepository.updateMedicalRecord(height,weight,diopter,alergies,bloodType,id);
    }
    public MedicalRecord save(MedicalRecord mr) {return medicalRecordRepository.save(mr);}
}