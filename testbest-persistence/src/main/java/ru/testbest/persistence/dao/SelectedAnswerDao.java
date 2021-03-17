package ru.testbest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.SelectedAnswer;

@Repository
public interface SelectedAnswerDao extends JpaRepository<SelectedAnswer, String> {

}
