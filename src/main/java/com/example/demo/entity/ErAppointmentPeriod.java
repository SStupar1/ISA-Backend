package com.example.demo.entity;

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
@AllArgsConstructor
@NoArgsConstructor
public class ErAppointmentPeriod extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "er_id", nullable = false)
    private Er er;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "appointment_period_id")
//    private AppointmentPeriod appointmentPeriod;

    private Date date;

    private LocalTime startAt;

    private LocalTime endAt;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar_id", referencedColumnName = "id")
    private Calendar calendar;

    private float price;
}


