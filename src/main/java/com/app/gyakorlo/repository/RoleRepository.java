package com.app.gyakorlo.repository;

import com.app.gyakorlo.model.ERole;
import com.app.gyakorlo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
