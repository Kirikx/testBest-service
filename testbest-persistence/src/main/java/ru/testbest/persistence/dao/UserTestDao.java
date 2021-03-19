package ru.testbest.persistence.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.UserTest;

@Repository
public interface UserTestDao extends JpaRepository<UserTest, String> {

  List<UserTest> findAllByUserId(String userId);

  List<UserTest> findAllByUserIdAndFinishedIsNull(String userId);

}
