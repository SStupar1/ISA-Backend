package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "diopter")
    private Double diopter;

    @Column(name = "alergies")
    private String alergies;

    @Column(name = "blood_type")
    private String bloodType;

    @OneToOne(mappedBy = "medicalRecord")
    private Patient patient;
}
