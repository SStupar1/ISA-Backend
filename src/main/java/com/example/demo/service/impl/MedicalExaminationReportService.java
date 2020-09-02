package com.example.demo.service.impl;


import com.example.demo.dto.request.MedicalExaminationReportDTORequest;
import com.example.demo.entity.MedicalExaminationReport;
import com.example.demo.entity.MedicalStaff;
import com.example.demo.entity.Patient;
import com.example.demo.repository.IMedicalStaffRepository;
import com.example.demo.repository.IPatientRepository;
import com.example.demo.repository.MedicalExaminationReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class MedicalExaminationReportService {
    @Autowired
    private MedicalExaminationReportRepository medicalExaminationReportRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IMedicalStaffRepository medicalStaffRepository;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private MedicineService medicineService;

    public MedicalExaminationReport createNew(MedicalExaminationReportDTORequest medicalExaminationDTO) {
        MedicalExaminationReport medicalExaminationReport = new MedicalExaminationReport();
        medicalExaminationReport.setDescription(medicalExaminationDTO.getDescription());
        MedicalStaff ms;
        try {
            ms = medicalStaffRepository.findOneById(medicalExaminationDTO.getDoctor());
            medicalExaminationReport.setDoctor(ms);
        }catch(Exception e){
            e.printStackTrace();
        }
        Patient p;
        try {
            p = patientRepository.findOneById(medicalExaminationDTO.getPatient());
            medicalExaminationReport.setPatient(p);
        }catch(Exception e){
            e.printStackTrace();
        }

        medicalExaminationReport.setDiagnosis(diagnosisService.findOneById(medicalExaminationDTO.getDiagnosis()));
        medicalExaminationReport.setMedicine(medicineService.findOneById(medicalExaminationDTO.getMedicine()));


        return medicalExaminationReportRepository.save(medicalExaminationReport);

    }
    public Set<MedicalExaminationReport> findByPatientId(UUID id){
        Set<MedicalExaminationReport> mer = medicalExaminationReportRepository.findAllPatients(id);
        return mer;
    }
}