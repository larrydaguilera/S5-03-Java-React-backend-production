
package com.ncs503.Babybook.models.response;

import com.ncs503.Babybook.models.entity.GuestEntity;
import com.ncs503.Babybook.models.entity.RoleEntity;
import com.ncs503.Babybook.models.entity.SubjectEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Leonardo Terlizzi
 */
@Data
@NoArgsConstructor
public class UserResponse {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String photo;


   // private Set<RoleEntity> rol_id;
    private List<SubjectGuestResponse> subjects;
    private List<GuestResponse> guests;
    
}
