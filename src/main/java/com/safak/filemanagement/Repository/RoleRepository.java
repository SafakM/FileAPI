package com.safak.filemanagement.Repository;

import com.safak.filemanagement.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<RoleEntity,String> {
    Optional<RoleEntity> findByRolename(String name);
}
