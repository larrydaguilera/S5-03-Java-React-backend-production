package com.ncs503.Babybook.models.request.specification;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Leonardo Terlizzi
 */
@Getter
@Setter
public class GuestsByUserRequest {

    private Long idUser;

    private String order;

    public GuestsByUserRequest(Long idUser, String order) {
        this.idUser = idUser;
        this.order = order;
    }

    public boolean isASC(){
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    public boolean isDESC(){
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}
