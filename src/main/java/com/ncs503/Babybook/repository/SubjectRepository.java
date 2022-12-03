package com.ncs503.Babybook.repository;

import com.ncs503.Babybook.models.entity.SubjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity,Long>,
                                            JpaSpecificationExecutor<SubjectEntity> {

    @Override
    List<SubjectEntity> findAll(Specification<SubjectEntity> specification);

    @Override
    Page<SubjectEntity> findAll(@Nullable Specification<SubjectEntity> specification, Pageable var2);

    @Query(value = "SELECT * from subjects WHERE subjects.first_name LIKE %:name% AND subjects.user_id = :id  AND soft_delete = false",
            nativeQuery = true)
    Optional<SubjectEntity> findByName(String name, Long id);

    @Query(value = "SELECT * from subjects WHERE subjects.user_id = :id AND soft_delete = false",
            nativeQuery = true)
    Optional<SubjectEntity> findByUserId(Long id);

}
