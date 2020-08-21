package com.example.demo.entity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(mappedBy = "patients")
    private Set<Clinic> clinics;

    @ManyToMany(mappedBy = "patients")
    private Set<MedicalStaff> medicals;

    private Boolean active;
}
