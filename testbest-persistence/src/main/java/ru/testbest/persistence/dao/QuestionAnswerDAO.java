package ru.testbest.persistence.dao;

import ru.testbest.dto.QuestionAnswer;
import java.util.List;

public interface QuestionAnswerDAO {

    QuestionAnswer getQuestionAnswerById(Integer id);

    List<QuestionAnswer> getAllQuestionAnswers();

    boolean deleteQuestionAnswer(QuestionAnswer questionAnswer);

    boolean updateQuestionAnswer(QuestionAnswer questionAnswer);

    boolean createQuestionAnswer(QuestionAnswer questionAnswer);

    List<QuestionAnswer> getAllQuestionAnswersByQuestionId(Integer question_id);

    Integer validationAnswerInput(Integer question_id, String answer_user);
}
