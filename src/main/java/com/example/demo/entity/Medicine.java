package com.example.demo.entity;

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
public class Medicine extends BaseEntity {

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @OneToMany(mappedBy = "medicine",fetch = FetchType.LAZY)
    private Set<MedicalExaminationReport> medicalExaminationReports = new HashSet<>();
}
