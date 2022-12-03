package com.ncs503.Babybook.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 *
 * @author Leonardo Terlizzi
 */


@Data
@NoArgsConstructor
@Entity
@Table(name = "guests")
@SQLDelete(sql = "UPDATE guests SET soft_delete = true Where guests_id=?")
@Where(clause = "soft_delete=false")
public class GuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guests_id")
    private Long id;

    @NotNull(message = "The first name can't be null")
    @NotEmpty(message = "The first name can't be empty")
    @NotBlank(message = "The first name can't be blank")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "The last name can't be null")
    @NotEmpty(message = "The last name can't be empty")
    @NotBlank(message = "The last name can't be blank")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "The e-mail address can't be null")
    @NotEmpty(message = "The e-mail address can't be empty")
    @Email(message = "Please use a valid e-mail")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "soft_delete")
    private Boolean soft_delete = Boolean.FALSE;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user_id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;



}
