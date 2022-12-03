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
@ApiModel(description = "Class representing an Subject Image Response.")
public class SubjectImageResponse {

    @ApiModelProperty(notes = "Subject Id",
            example = "1",
            required = true)
    private Long id;


    @ApiModelProperty(notes = "Subject Profile Image",
            example = "imagen.jpg",
            required = true)
    private String image;
}
