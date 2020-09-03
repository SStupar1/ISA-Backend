package com.example.demo.entity;

import com.example.demo.util.enums.CalendarType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Calendar extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicalStaff_id")
    private MedicalStaff medicalStaff;

    private Date date;

    private LocalTime startAt;

    private LocalTime endAt;

    @Enumerated(EnumType.STRING)
    private CalendarType calendarType;

    private UUID patientId;
}
