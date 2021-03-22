package ru.testbest.persistence.dao;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Role;

@Repository
public interface RoleDao extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
