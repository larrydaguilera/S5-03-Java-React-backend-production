package com.ncs503.Babybook.repository.specification;

import com.ncs503.Babybook.models.entity.GuestEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.request.specification.GuestsByUserRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Leonardo Terlizzi
 */
@Component
public class GuestByUserSpecification {

    public Specification<GuestEntity> getByUsers(GuestsByUserRequest filterRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filterRequest.getIdUser() != null) {
                Join< GuestEntity, UserEntity> join = root.join("user_id", JoinType.INNER);
                Expression<String> user_id = join.get("id");
                predicates.add(user_id.in(filterRequest.getIdUser()));
            }

            query.distinct(true);

            String orderByField = "createdAt";
            query.orderBy(
                            filterRequest.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)):
                            criteriaBuilder.desc(root.get(orderByField))

            );
            return criteriaBuilder.and((Predicate[])
                           predicates.toArray(new Predicate[0]));

        };
    }


}
