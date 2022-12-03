package com.ncs503.Babybook.models.request;

import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.utility.BloodTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Class representing an Medical Data Request.")
public class MedicalDataRequest {

    @ApiModelProperty(notes = "Subject Blood Type",
            example = "CEROPOSITIVO",
            required = true)
    @Enumerated(value = EnumType.STRING)
    private BloodTypeEnum bloodType;

    @ApiModelProperty(notes = "Subject Alergies",
            example = "Maní",
            required = true)
    private String alergies;

    @ApiModelProperty(notes = "Relevant info of the subject that you want to add",
            example = "Es medio flojito de vientre",
            required = true)
    private String relevantInfo;

   @ApiModelProperty(notes = "id of the subject to which the medical data belongs", example = "1")
    private Long subject;


}
