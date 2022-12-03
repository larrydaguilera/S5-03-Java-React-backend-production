package com.ncs503.Babybook.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE subjects SET soft_delete = true Where subject_id=?")
@Where(clause = "soft_delete=false")
@Table( name= "subjects")

public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @Nullable
    private String image;

    @NotNull
    @NotEmpty(message = "name can not be null")
    @NotBlank(message = "name can not be blank")
    private String firstName;

    private String lastName;

    @NotNull
    @NotEmpty(message = "birth date can not be null")
    @NotBlank(message = "birth date can not be blank")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;

    @Pattern(regexp = "^\\d*$", message = "The dni has to contain only numbers")
    private String dni;

    @CreationTimestamp
    private Timestamp timestamp;

    @Column(name = "soft_delete")
    private boolean deleted = Boolean.FALSE;


    //TODO Ver relaciones con Events
//     relacion bidireccional
    @JsonIgnore
    @OneToMany(mappedBy = "subjectEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EventEntity> eventEntities;


    @JsonIgnore
    @OneToOne(mappedBy = "subject", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "medicalData_id")
    private MedicalDataEntity medicalDataEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity users;



}
