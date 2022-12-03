package com.ncs503.Babybook.repository.specification;


import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.request.specification.SubjectByUserRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectByUserSpecification {

    public Specification<SubjectEntity> getByUsers(SubjectByUserRequest filterRequest) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filterRequest.getIdUser() != null) {
                Join< SubjectEntity, UserEntity> join = root.join("users", JoinType.INNER);//nombre del atributo
                Expression<String> idUser = join.get("id"); //nombre de la columna
                predicates.add(idUser.in(filterRequest.getIdUser()));
            }


            //remueve duplicados
            query.distinct(true);

            // Resuelve el orden
            String orderByField = "timestamp"; //"date = nombre del atributo
            query.orderBy(
                    filterRequest.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );

            return criteriaBuilder.and((Predicate[])
                    predicates.toArray(new Predicate[0]));
        };
    }


}
