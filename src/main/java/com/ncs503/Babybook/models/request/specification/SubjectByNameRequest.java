package com.ncs503.Babybook.models.request.specification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectByNameRequest {

    private String firstName;
    private String order;

    private Long id;

    public SubjectByNameRequest(String firstName, String order, Long id) {
       this.firstName=firstName;
        this.order = order;
        this.id = id;
    }

    public boolean isASC(){ return this.order.compareToIgnoreCase("ASC") == 0;}
    public boolean isDESC(){ return this.order.compareToIgnoreCase("DESC") == 0;}
}
