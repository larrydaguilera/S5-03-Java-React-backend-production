package com.ncs503.Babybook.repository.specification;


import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.request.specification.SubjectByNameRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectByNameSpecification {

    public Specification<SubjectEntity> getByName(SubjectByNameRequest filterRequest) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filterRequest.getFirstName())) { // pregunta si tiene algo el filter
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), //nombre del atributo
                        "%" + filterRequest.getFirstName().toLowerCase() + "%"
                ));

            }

            if (filterRequest.getId() != null) {
                Join< SubjectEntity, UserEntity> join = root.join("users", JoinType.INNER);//nombre del atributo
                Expression<String> idUser = join.get("id"); //nombre de la columna
                predicates.add(idUser.in(filterRequest.getId()));
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

            return criteriaBuilder.and((javax.persistence.criteria.Predicate[])
                    predicates.toArray(new Predicate[0]));
        };
    }


}
