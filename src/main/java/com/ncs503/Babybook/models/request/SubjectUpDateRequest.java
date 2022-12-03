package com.ncs503.Babybook.models.request;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Class representing an Subject update data  Request.")
public class SubjectUpDateRequest {

    @NonNull
    @NotEmpty(message = "the name can't be null")
    @NotBlank(message = "the name can't  be blank")
    @ApiModelProperty(notes = "Subject name",
            example = "José", required = true)
    private String firstName;

    @ApiModelProperty(notes = "Subject name",
            example = "Ibarrondo", required = true)
    private String lastName;

    @NotNull
    @NotEmpty(message = "birth date can not be null")
    @NotBlank(message = "birth date can not be blank")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @ApiModelProperty(notes = "Subject birth date",
            example = "1979-10-11", required = true)
    private LocalDate birthDate;

    @ApiModelProperty(notes = "Subject DNI",
            example = "4555666", required = true)
    //  @Pattern(regexp = "^\\d*$", message = "The dni has to contain only numbers")
    private String dni;


}
