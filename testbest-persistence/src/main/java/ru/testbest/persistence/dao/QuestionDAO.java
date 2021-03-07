package ru.testbest.persistence.dao;

import ru.testbest.dto.Question;
import java.util.List;

public interface QuestionDAO {

    Question getQuestionById(Integer id);

    List<Question> getAllQuestions();

    List<Question> getAllQuestionsByTopicId(Integer id);

    boolean deleteQuestion(Question question);

    boolean updateQuestion(Question question);

    boolean createQuestion(Question question);

    Question getQuestionByIdNoDetails(Integer s_id);

}
