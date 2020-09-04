package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Er extends BaseEntity {

    @Column(unique = true)
    private Long number;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", referencedColumnName = "id")
    private Clinic clinic;

    @OneToMany(mappedBy = "er", fetch = FetchType.LAZY)
    private List<ErAppointmentPeriod> erAppointmentPeriods;

    boolean isDeleted;

    //vise adminitratora ne sme da izmeni istu salu u isto vreme
    @Version
    @Column(name="version",columnDefinition = "integer DEFAULT 0",nullable = false)
    private int version;
}
