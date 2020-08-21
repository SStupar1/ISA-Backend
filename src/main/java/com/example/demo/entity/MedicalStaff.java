package com.example.demo.entity;

import com.example.demo.utils.enums.MedicalType;
import com.example.demo.utils.enums.MedicalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalStaff extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Enumerated(EnumType.STRING)
    private MedicalType medicalType;
/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
*/
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "medical_patient",
            joinColumns = @JoinColumn(name = "medical_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private Set<Patient> patients;
/*
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "medicalStaff", cascade = CascadeType.ALL)
    private List<Calendar> calendars;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointmentType_id", referencedColumnName = "id")
    private AppointmentType appointmentType;

    @OneToMany(mappedBy = "nurse",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    Set<Recipe> recipes = new HashSet<>();
*/
    private LocalTime startWorkAt;

    private LocalTime endWorkAt;
/*
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Grade> grades;
 */
}
