package com.example.demo.controller;


import com.example.demo.dto.request.MedicalExaminationReportDTORequest;
import com.example.demo.dto.response.MedicalExaminationDTOResponse;
import com.example.demo.dto.response.MedicalRecordDTOResponse;
import com.example.demo.entity.MedicalExaminationReport;
import com.example.demo.entity.Recipe;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.impl.MedicalExaminationReportService;
import com.example.demo.service.impl.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/medicalExamination")
public class MedicalExaminationReportController {

    @Autowired
    MedicalExaminationReportService medicalExaminationReportService;

    @Autowired
    RecipeService recipeService;

    @RequestMapping(value = "/new",consumes = "application/json" ,method = RequestMethod.POST)
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> addNewReport(@RequestBody MedicalExaminationReportDTORequest medicalExaminationDTO){
        MedicalExaminationReport medicalExaminationReport = medicalExaminationReportService.createNew(medicalExaminationDTO);
        Recipe recipe = recipeService.createNew(medicalExaminationDTO);
        MedicalExaminationReportDTORequest mr= new MedicalExaminationReportDTORequest(medicalExaminationReport);
        return new ResponseEntity<>(mr, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getAll/{id}",method = RequestMethod.GET)
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> getAllForSiglePatient(@PathVariable UUID id){

        Set<MedicalExaminationReport> medicalExaminationReports = medicalExaminationReportService.findByPatientId(id);
        Set<MedicalExaminationDTOResponse> medicalExaminationResponseDTOSet = new HashSet<>();
        for(MedicalExaminationReport m : medicalExaminationReports){
            medicalExaminationResponseDTOSet.add(new MedicalExaminationDTOResponse(m));
        }
        return new ResponseEntity<>(medicalExaminationResponseDTOSet,HttpStatus.OK);
    }
}
