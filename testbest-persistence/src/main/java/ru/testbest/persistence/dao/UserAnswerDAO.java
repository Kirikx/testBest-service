package ru.testbest.persistence.dao;

import ru.testbest.dto.UserAnswer;
import java.util.List;

public interface UserAnswerDAO {

    UserAnswer getUserAnswerById(Integer id);

    List<UserAnswer> getAllUsersAnswers();

    boolean deleteUserAnswer(UserAnswer userAnswer);

    boolean updateUserAnswer(UserAnswer userAnswer);

    boolean createUserAnswer(UserAnswer userAnswer);

    boolean validationCheckQuestion(Integer user_id, Integer question_id);

    List<UserAnswer> getAllUserAnswers(Integer user_id);
}
