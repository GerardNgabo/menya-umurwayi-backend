package com.hospitalMgt.patiencemgt.repositories;

import com.hospitalMgt.patiencemgt.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
