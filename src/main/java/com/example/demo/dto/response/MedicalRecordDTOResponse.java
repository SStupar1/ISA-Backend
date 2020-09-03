package com.example.demo.dto.response;

import com.example.demo.entity.MedicalRecord;
import com.example.demo.entity.Patient;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MedicalRecordDTOResponse {
    private Long id;
    private Double height;
    private Double weight;
    private Double diopter;
    private String alergies;
    private String bloodType;

    private String firstName;
    private String lastName;
    private String jmbg;

    public MedicalRecordDTOResponse(Long id, Double height, Double weight, Double diopter, String alergies, String bloodType,String firstName,String lastName,String jmbg) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.diopter = diopter;
        this.alergies = alergies;
        this.bloodType = bloodType;
        this.firstName =firstName;
        this.lastName = lastName;
        this.jmbg = jmbg;
    }
    public MedicalRecordDTOResponse(MedicalRecord record, Patient p){
        this(record.getId(),record.getHeight(), record.getWeight(), record.getDiopter(),record.getAlergies(), record.getBloodType(),p.getUser().getFirstName(),p.getUser().getLastName(),p.getUser().getSsn());
    }
    public MedicalRecordDTOResponse() {

    }
}
