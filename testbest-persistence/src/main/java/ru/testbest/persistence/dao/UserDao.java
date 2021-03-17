package ru.testbest.persistence.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String> {

  List<User> findByRoleId(String roleId);

  User findByLogin(String login);

}
