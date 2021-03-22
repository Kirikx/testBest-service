package ru.testbest.persistence.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, UUID> {

  Optional<User> findByIdAndIsDeletedFalse(UUID id);

  List<User> findAllByIsDeletedFalse();

  Optional<User> findByUsernameAndIsDeletedFalse(String username);

  Boolean existsByUsernameAndIsDeletedFalse(String username);

  Boolean existsByEmailAndIsDeletedFalse(String email);

}
