package com.ncs503.Babybook.models.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Leonardo Terlizzi
 */

@Data
@NoArgsConstructor
@ApiModel(description = "Class representing a Guest Request")
public class GuestRequest {

    @ApiModelProperty(notes="Guest's id", example= "1")
    private Long id;

    @NotNull(message = "the first name can't be null")
    @NotEmpty(message = "the first name can't be empty")
    @NotBlank(message = "the first name can't be blank")
    @ApiModelProperty(notes= "Guest's first name", example= "Jose", required = true)
    private String firstName;

    @NotNull(message = "The last name can't be null")
    @NotBlank(message = "The last name can't be blank")
    @NotEmpty(message = "The last name can't be empty")
    @ApiModelProperty(notes = "Guest's last name", example= "Lopez", required = true)
    private String lastName;

    @NotNull(message = "The e-mail address can't be null")
    @Email(message = "Please use a valid e-mail address")
    @NotEmpty(message = "The e-mail address can't be empty")
    @ApiModelProperty(notes = "Guest's e-mail address", example = "joselopez123@yahoo.com", required = true)
    private String email;

    @NotNull(message = "The user's id can't be null")
    @NotEmpty(message = "The user's id can't be empty")
    @ApiModelProperty(notes = "Guest's associated user's id", example = "1", required = true)
    private Long user_id;


}
