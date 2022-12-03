
package com.ncs503.Babybook.models.request;


import com.ncs503.Babybook.models.entity.GuestEntity;
import com.ncs503.Babybook.models.entity.RoleEntity;
import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.response.GuestResponse;
import com.ncs503.Babybook.models.response.SubjectResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Leonardo Terlizzi
 */

@Data
@NoArgsConstructor
@ApiModel(description = "Class representing an User Request.")
public class UserRequest {
    
 /*   @ApiModelProperty(notes = "User's id", example = "1", required = false)
    private Long id;*/
    
    @NotNull(message = "the firstName can't be null")
    @NotEmpty(message = "the firstName can't be empty")
    @NotBlank(message = "the firstName can't  be blank")
    @ApiModelProperty(notes = "First name of the User.",
            example = "Juan", required = true)
    private String firstName;
    
    @NotNull(message = "the lastName can't be null")
    @NotEmpty(message = "the lastName can't be empty")
    @NotBlank(message = "the lastName can't  be blank")
    @ApiModelProperty(notes = "Last name of the User.",
            example = "Perez", required = true)
    private String lastName;
    
    @NotNull(message = "the username can't be null")
    @NotEmpty(message = "the username can't be empty")
    @NotBlank(message = "the username can't  be blank")
    @ApiModelProperty(notes = "THE Username of the User.",
            example = "juanperez84", required = true)
    private String username;
    
    @NotNull(message = "The e-mail address can't be null")
    @Email(message = "Use a valid e-mail address")
    @ApiModelProperty(notes = "The user's e-mail address",
            example = "juanperez84@gmail.com", required = true)
    private String email;
    
    @NotNull(message = "The password can't be null")
    @NotEmpty(message = "The password can't be empty")
    @NotBlank(message = "The password can't be blank")
    @ApiModelProperty(notes = "The user's password",
            example = "juancho123", required = true)
    private String password;
    
    @ApiModelProperty(notes = "Base64 encoded profile picture",
            example= "data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUA")
    private String photo;

    private Set<RoleEntity> rol_id;
    private List<SubjectRequest> subjects;
    private List<GuestRequest> guests;
    
}
