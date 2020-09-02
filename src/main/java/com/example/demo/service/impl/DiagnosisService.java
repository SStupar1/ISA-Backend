package com.example.demo.service.impl;


import com.example.demo.dto.request.DiagnosisDTORequest;
import com.example.demo.entity.Diagnosis;
import com.example.demo.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class DiagnosisService {
    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public Diagnosis findOneByName(String name) {return diagnosisRepository.findByName(name);}
    public Diagnosis findOneById(UUID id) {return diagnosisRepository.findOneById(id);}
    public List<Diagnosis> findAll() { return diagnosisRepository.findAll();}
    /**
     * Potrebno je enkapsulirati ovo funkcionalnost u transakciju zato sto moze doci do situacije u kojoj dva administratora u isto vremen pokusavaju da izmene istu diagnozu
     * @param diagnosisDTO
     * @return
     */
    public Diagnosis save(DiagnosisDTORequest diagnosisDTO) {
        Diagnosis d = new Diagnosis();
        d.setName(diagnosisDTO.getName());
        return diagnosisRepository.save(d);
    }
}