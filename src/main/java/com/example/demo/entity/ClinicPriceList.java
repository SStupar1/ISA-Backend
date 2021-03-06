package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClinicPriceList extends BaseEntity {
    
    Clinic clinic;


    AppointmentType appointmentType;


    float price;
}
