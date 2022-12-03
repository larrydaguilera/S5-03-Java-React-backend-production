package com.ncs503.Babybook.repository;

import com.ncs503.Babybook.models.entity.GuestEntity;
import com.ncs503.Babybook.models.entity.SubjectEntity;
import com.ncs503.Babybook.models.entity.UserEntity;
import com.ncs503.Babybook.models.response.GuestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Leonardo Terlizzi
 */

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity, Long>, JpaSpecificationExecutor<SubjectEntity> {

//    @Query(value = "SELECT * from guests WHERE guests.email LIKE %:email% AND softDelete = false", nativeQuery = true)
    Optional<GuestEntity> findByEmail(String email);

    @Query(value = "SELECT * from guests WHERE guests.user_id = :id AND soft_delete = false", nativeQuery = true)
    Optional<List<GuestEntity>> findByUserId(Long id);
}
