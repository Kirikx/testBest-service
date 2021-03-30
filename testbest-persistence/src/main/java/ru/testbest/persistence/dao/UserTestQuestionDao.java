package ru.testbest.persistence.dao;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.UserTestQuestion;

@Repository
public interface UserTestQuestionDao extends JpaRepository<UserTestQuestion, UUID> {

  List<UserTestQuestion> findAllByUserTestId(UUID userTestId);

}
