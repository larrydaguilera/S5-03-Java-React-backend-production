package com.ncs503.Babybook.models.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ApiModel(description = "Class representing an Subject Response.")
public class SubjectResponse {

    @ApiModelProperty(notes = "Subject Id",
            example = "1",
            required = true)
    private Long id;


    @ApiModelProperty(notes = "Subject Profile Image",
            example = "imagen.jpg" )
    private String image;


    @ApiModelProperty(notes = "Subject name",
            example = "José", required = true)
    private String firstName;

    @ApiModelProperty(notes = "Subject name",
            example = "Ibarrondo", required = false)
    private String lastName;


    @ApiModelProperty(notes = "Subject birth date",
            example = "1979/10/11", required = true)
    private LocalDate birthDate;

    @ApiModelProperty(notes = "Subject DNI",
            example = "5236788", required = true)
    private String dni;

    @ApiModelProperty(notes = "id of the user corresponding to the Subject", example = "1")
    private Long idUser;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @ApiModelProperty(notes = "Subject create date",
            example = "2022/11/11", required = true)
    private Timestamp createDate;
}
