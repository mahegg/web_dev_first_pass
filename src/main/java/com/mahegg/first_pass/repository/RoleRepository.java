package com.mahegg.first_pass.repository;

import com.mahegg.first_pass.enums.RoleEnum;
import com.mahegg.first_pass.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);

}
