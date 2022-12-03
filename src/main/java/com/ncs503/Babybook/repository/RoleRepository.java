package com.ncs503.Babybook.repository;

import com.ncs503.Babybook.models.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Leonardo Terlizzi
 */

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Set<RoleEntity> findByName(String name);
}
