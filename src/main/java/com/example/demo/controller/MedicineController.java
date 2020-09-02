package com.example.demo.controller;
import com.example.demo.dto.request.MedicineDTORequest;
import com.example.demo.entity.Medicine;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.service.impl.MedicineService;
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
@RequestMapping(value = "api/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;


    @RequestMapping(value = "/addMedicine",consumes = "application/json")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> addMedicine(@RequestBody MedicineDTORequest medicineDTO){
        Medicine medicine = medicineService.findOneByName(medicineDTO.getName());
        if(medicine != null) {
            return new ResponseEntity<>(new MedicineDTORequest(medicine), HttpStatus.BAD_REQUEST);
        }
        medicine = medicineService.save(medicineDTO);
        return new ResponseEntity<>(new MedicineDTORequest(medicine), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/getAll")
    @PreAuthorize(AuthoritiesConstants.ADMIN_PATIENT_MEDICAL_ROLE)
    public ResponseEntity<?> getAllMedicine() {
        List<Medicine> medicines = medicineService.findAll();
        List<MedicineDTORequest> medicineDTOS = new ArrayList<>();
        for(Medicine m : medicines){
            medicineDTOS.add(new MedicineDTORequest(m));
        }
        return new ResponseEntity<>(medicineDTOS,HttpStatus.OK);
    }
}
