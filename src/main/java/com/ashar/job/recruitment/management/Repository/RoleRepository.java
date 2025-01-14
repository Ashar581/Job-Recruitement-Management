package com.ashar.job.recruitment.management.Repository;

import com.ashar.job.recruitment.management.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleCode(String roleCode);
    Optional<Role> findByRoleName(String roleName);
}
