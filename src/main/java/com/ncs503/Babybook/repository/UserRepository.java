
package com.ncs503.Babybook.repository;

import com.ncs503.Babybook.models.entity.GuestEntity;
import com.ncs503.Babybook.models.entity.UserEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Leonardo Terlizzi
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    

   // @Query(value = "SELECT * from users WHERE users.email LIKE %:email% AND deleted = false", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String userName);


}
