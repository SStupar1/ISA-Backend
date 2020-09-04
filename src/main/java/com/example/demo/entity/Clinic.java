package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clinic extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_center_id")
    private ClinicCenter clinicCenter;

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Admin> admins = new ArrayList<>();

    @OneToMany(mappedBy = "clinic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalStaff> medicals = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "clinic_patient",
            joinColumns = @JoinColumn(name = "clinic_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<Patient> patients;

    private String name;

    private String address;

    private String description;

    private String lat;

    private String lon;

    boolean isDeleted;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Grade> grades;

    //ako je neka klinika izmenjena, dodata ili obrisana u momentu kada je pacijent prikazao klinike, pacijent ce dobiti nevalidne podatke
    //vise adminitratora ne sme da izmeni istu kliniku u isto vreme
    @Version
    @Column(name="version",columnDefinition = "integer DEFAULT 0",nullable = false)
    private int version;
}
