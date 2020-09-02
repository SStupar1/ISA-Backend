package com.example.demo.service.impl;

import com.example.demo.dto.request.MedicalExaminationReportDTORequest;
import com.example.demo.entity.MedicalStaff;
import com.example.demo.entity.Medicine;
import com.example.demo.entity.Patient;
import com.example.demo.entity.Recipe;
import com.example.demo.repository.IMedicalStaffRepository;
import com.example.demo.repository.IPatientRepository;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IMedicalStaffRepository medicalStaffRepository;




    public Recipe createNew(MedicalExaminationReportDTORequest medicalExaminationDTO) {

        Recipe recipe = new Recipe();
        Medicine m = medicineRepository.findOneById(medicalExaminationDTO.getMedicine());
        String medicineName = m.getName();
        recipe.setMedicine(medicineName);
        Patient p;
        try {
            recipe.setPatient(patientRepository.findOneById(medicalExaminationDTO.getPatient()));
        }catch(Exception e) {
            e.printStackTrace();
        }

        return recipeRepository.save(recipe);

    }
    public int updateRecipe(UUID nurse, UUID id) {
        MedicalStaff n = medicalStaffRepository.findOneById(nurse);
        return recipeRepository.updateUserStatus(n,id);
    }
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }
}
