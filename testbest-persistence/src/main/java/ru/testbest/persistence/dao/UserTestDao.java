package ru.testbest.persistence.dao;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.UserTest;

@Repository
public interface UserTestDao extends JpaRepository<UserTest, UUID> {

  List<UserTest> findAllByUserId(UUID userId);

//  Optional<UserTest> findLastByUserIdAndStarted(UUID userId); //TODO проверить

}