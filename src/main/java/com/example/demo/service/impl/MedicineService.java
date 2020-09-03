package com.example.demo.service.impl;

import com.example.demo.dto.request.MedicineDTORequest;
import com.example.demo.entity.Medicine;
import com.example.demo.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;

    public Medicine findOneByName(String name) {return medicineRepository.findByName(name);}
    public Medicine findOneById(UUID id) {return medicineRepository.findOneById(id);}
    public List<Medicine> findAll() { return medicineRepository.findAll();}

    public Medicine save(MedicineDTORequest medicineDTO) {
        Medicine m = new Medicine();
        m.setName(medicineDTO.getName());
        return medicineRepository.save(m);
    }
}
