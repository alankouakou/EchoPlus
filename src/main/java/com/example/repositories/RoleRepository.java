package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
