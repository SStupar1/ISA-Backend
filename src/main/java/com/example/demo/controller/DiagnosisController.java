package com.example.demo.controller;

import com.example.demo.dto.request.DiagnosisDTORequest;
import com.example.demo.entity.Diagnosis;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.impl.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/diagnosis")
public class DiagnosisController {
    @Autowired
    private DiagnosisService diagnosisService;

    @RequestMapping(value = "/addDiagnosis",consumes = "application/json")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> addMedicine(@RequestBody DiagnosisDTORequest diagnosisDTO){
        Diagnosis diagnosis= diagnosisService.findOneByName(diagnosisDTO.getName());
        if(diagnosis != null) {
            return new ResponseEntity<>(new DiagnosisDTORequest(diagnosis), HttpStatus.BAD_REQUEST);
        }
        diagnosis = diagnosisService.save(diagnosisDTO);
        return new ResponseEntity<>(new DiagnosisDTORequest(diagnosis), HttpStatus.CREATED);
    }


    @RequestMapping(value = "/getAll")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> getAllMedicine() {
        List<Diagnosis> diagnosises = diagnosisService.findAll();
        List<DiagnosisDTORequest> diagnosisDTOS = new ArrayList<>();
        for(Diagnosis d : diagnosises){
            diagnosisDTOS.add(new DiagnosisDTORequest(d));
        }
        return new ResponseEntity<>(diagnosisDTOS,HttpStatus.OK);
    }
}

