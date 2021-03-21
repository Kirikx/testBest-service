package ru.testbest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Role;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
