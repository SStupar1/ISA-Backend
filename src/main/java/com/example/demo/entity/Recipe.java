package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Recipe extends BaseEntity{

    @Column(name = "medicine",nullable = false)
    private String medicine;


    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private MedicalStaff nurse;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Patient patient;
}
