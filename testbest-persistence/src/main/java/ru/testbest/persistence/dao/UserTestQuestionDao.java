package ru.testbest.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.UserTestQuestion;

@Repository
public interface UserTestQuestionDao extends JpaRepository<UserTestQuestion, String> {

}
