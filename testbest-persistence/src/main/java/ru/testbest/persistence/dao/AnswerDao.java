package ru.testbest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.Answer;

@Repository
public interface AnswerDao extends JpaRepository<Answer, String> {

}
