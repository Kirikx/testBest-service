package ru.testbest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.UserTest;

@Repository
public interface UserTestDao extends JpaRepository<UserTest, String> {

}
