package ru.testbest.persistence.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, UUID> {

  Optional<Question> findByIdAndIsDeletedFalse(UUID id);

  List<Question> findAllByIsDeletedFalse();

}
