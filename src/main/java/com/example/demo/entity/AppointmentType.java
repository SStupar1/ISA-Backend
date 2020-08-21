package com.example.demo.entity;

import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentType extends BaseEntity {

    private String name;

    private float price;

    boolean isDeleted;
}

