package com.example.sotisproject.repository;

import com.example.sotisproject.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
        Role findByName(String name);
}
