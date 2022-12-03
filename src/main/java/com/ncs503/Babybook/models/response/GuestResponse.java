package com.ncs503.Babybook.models.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Leonardo Terlizzi
 */
@Data
@NoArgsConstructor
public class GuestResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long user_id;
}
