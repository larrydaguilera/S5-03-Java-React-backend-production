package com.ncs503.Babybook.models.response;

import com.ncs503.Babybook.models.utility.BloodTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ApiModel(description = "Class representing an Medical Data Response.")
public class MedicalDataResponse {

    @ApiModelProperty(notes = "Medical Data Id",
            example = "1",
            required = true)
    private Long id;

    @ApiModelProperty(notes = "Subject Blood Type",
            example = "0+",
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

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @ApiModelProperty(notes = "Medical Data  create date",
            example = "2022/11/11", required = true)
    private Timestamp createDate;
}
