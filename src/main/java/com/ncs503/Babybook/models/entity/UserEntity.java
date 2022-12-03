
package com.ncs503.Babybook.models.entity;



import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

/**
 *
 * @author Leonardo Terlizzi
 */

@Data
@NoArgsConstructor
@Entity
@Table ( name = "users")
@SQLDelete(sql = "UPDATE users SET soft_delete = true Where id=?")
@Where(clause="soft_delete=false")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull(message = "The first name can't be null")
    @NotEmpty(message = "The first name can't  be empty")
    @NotBlank(message = "The first name can't be blank")
    @Column( name = "first_name", nullable = false)
    private String firstName;
    
    @NotNull(message = "The last name can't be null")
    @NotEmpty(message = "The last name can't  be empty")
    @NotBlank(message = "The last name can't be blank")
    @Column( name = "last_name", nullable = false)
    private String lastName;
    
    
    @NotNull(message = "The username can't be null")
    @NotEmpty(message = "The username can't be empty")
    @NotBlank(message = "The username can't be blank")
    @Column( name = "username", nullable = false)
    private String username;
    
    @Column( name = "photo", nullable = true)
    private String photo;
    

    @NotNull(message = "The e-mail address can't be null")
    @Email(message ="Please use a valid e-mail address")
    @NotEmpty(message = "The e-mail address can't be empty")
    @Column( name = "email", nullable = false)
    private String email;
    
    @NotNull(message = "The password can't be null")
    @NotEmpty(message = "The password can't be empty")
    @Column( name = "password", nullable = false)
    private String password;
    



    @JsonIgnore
    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubjectEntity> subjects;


    @OneToMany(mappedBy = "user_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GuestEntity> guests = new ArrayList<>();
    
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;
    
    @UpdateTimestamp
    private Timestamp updateAt;

    @Column(name = "soft_delete")
    private Boolean soft_delete = Boolean.FALSE;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn(name= "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<RoleEntity> roleId;


    
}
