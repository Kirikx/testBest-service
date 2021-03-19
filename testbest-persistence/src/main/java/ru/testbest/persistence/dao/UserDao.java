package ru.testbest.persistence.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {

  //  List<User> findByRoleId(String roleId);
  Optional<User> findByIdAndIsDeletedFalse(String uuid);

  List<User> findAllByIsDeletedFalse();

  Optional<User> findByUsernameAndIsDeletedFalse(String login);

}
