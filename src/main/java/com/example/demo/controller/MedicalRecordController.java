package com.example.demo.controller;

import com.example.demo.dto.response.MedicalRecordDTOResponse;
import com.example.demo.entity.MedicalRecord;
import com.example.demo.entity.Patient;
import com.example.demo.repository.IPatientRepository;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.impl.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "api/medicalRecord")
public class MedicalRecordController {
    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    IPatientRepository patientRepository;


    @RequestMapping(value="/getByPatientId",method = RequestMethod.GET)
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> getById(@RequestParam UUID id){
        Patient personRet = patientRepository.findOneById(id);
        MedicalRecord mr = personRet.getMedicalRecord();
        if(mr == null){
            MedicalRecord medicalRecord = new MedicalRecord();
            medicalRecordService.save(mr);
            personRet.setMedicalRecord(mr);
        }
        MedicalRecordDTOResponse md = new MedicalRecordDTOResponse(mr,personRet);
        return new ResponseEntity<>(md, HttpStatus.OK);

    }

    @RequestMapping(consumes = "application/json",value = "/update",method = RequestMethod.POST)
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> update(@RequestBody MedicalRecordDTOResponse recordDTO){

        medicalRecordService.updateMedicalRecord(recordDTO.getHeight(),recordDTO.getWeight(),recordDTO.getDiopter(), recordDTO.getAlergies(),recordDTO.getBloodType(),recordDTO.getId());
        return new ResponseEntity<>(new MedicalRecordDTOResponse(),HttpStatus.OK);

    }
}
