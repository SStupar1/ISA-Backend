package com.example.demo.entity;

import com.example.demo.utils.enums.AppointmentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medical_staff_id", referencedColumnName = "id")
    private MedicalStaff medicalStaff;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "er_appointment_period_id", referencedColumnName = "id")
    private  ErAppointmentPeriod erAppointmentPeriod;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private Clinic clinic;

    private float price;

    @Enumerated(EnumType.STRING)
    private AppointmentTypeEnum appType;
}
