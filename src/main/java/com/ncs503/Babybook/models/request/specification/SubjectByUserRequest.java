package com.ncs503.Babybook.models.request.specification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectByUserRequest {

    private Long idUser;
    private String order;

    public SubjectByUserRequest(Long idUser, String order) {
        this.idUser = idUser;
        this.order = order;
    }

    public boolean isASC(){ return this.order.compareToIgnoreCase("ASC") == 0;}
    public boolean isDESC(){ return this.order.compareToIgnoreCase("DESC") == 0;}
}
