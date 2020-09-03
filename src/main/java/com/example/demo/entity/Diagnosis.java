package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosis extends BaseEntity{

    @Column(name = "name",unique = true,nullable = false)
    private String name;

    @OneToMany(mappedBy = "diagnosis",fetch = FetchType.LAZY)
    private Set<MedicalExaminationReport> medicalExaminationReports = new HashSet<>();


}
