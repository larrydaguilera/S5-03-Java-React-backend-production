package com.ncs503.Babybook.models.response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ApiModel(description = "Class representing an Subject for Guest Response.")
public class SubjectGuestResponse {

    @ApiModelProperty(notes = "Subject Id",
            example = "1",
            required = true)
    private Long id;


    @ApiModelProperty(notes = "Subject Profile Image",
            example = "imagen.jpg",
            required = true)
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


    @ApiModelProperty(notes = "id of the user corresponding to the Subject", example = "1")
    private Long idUser;



}
