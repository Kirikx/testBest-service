package ru.testbest.persistence.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Answer;

@Repository
public interface AnswerDao extends JpaRepository<Answer, String> {

  Optional<Answer> findByIdAndIsDeletedFalse(String uuid);

  List<Answer> findAllByIsDeletedFalse();
}
