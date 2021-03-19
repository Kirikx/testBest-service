package ru.testbest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.QuestionType;

@Repository
public interface QuestionTypeDao extends JpaRepository<QuestionType, String> {

}
