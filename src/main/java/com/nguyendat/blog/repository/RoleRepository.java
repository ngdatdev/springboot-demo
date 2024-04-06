package com.nguyendat.blog.repository;

import com.nguyendat.blog.model.role.Role;
import com.nguyendat.blog.model.role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName role);
}
