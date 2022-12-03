package com.ncs503.Babybook.repository;

import com.ncs503.Babybook.models.entity.MedicalDataEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalDataRepository extends JpaRepository<MedicalDataEntity, Long> {

    @Query(value = "SELECT * " +
            "from medical_Data c " +
            "WHERE subject_subject_id = :id " +
            "AND soft_delete = false", nativeQuery = true)
    Optional<MedicalDataEntity> findBySubjectId(Long id);
}
