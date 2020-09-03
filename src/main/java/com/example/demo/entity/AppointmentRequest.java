package com.example.demo.entity;

import com.example.demo.util.enums.AppointmentTypeEnum;
import com.example.demo.util.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest extends BaseEntity {

    @Version
    @Column(name="version",columnDefinition = "integer DEFAULT 0",nullable = false)
    private int version;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointmentType_id", referencedColumnName = "id")
    private AppointmentType appointmentType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "medicalStaff_id", referencedColumnName = "id")
    private MedicalStaff medicalStaff;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    private Date appointmentDate;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private Clinic clinic;

    private LocalTime startAt;

    private LocalTime endAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "er_id", referencedColumnName = "id")
    private Er er;
}
