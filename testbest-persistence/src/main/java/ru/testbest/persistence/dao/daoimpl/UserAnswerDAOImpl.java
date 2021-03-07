package ru.testbest.persistence.dao.daoimpl;

import ru.testbest.dto.Question;
import ru.testbest.dto.UserAnswer;
import ru.testbest.persistence.dao.QuestionDAO;
import ru.testbest.persistence.dao.UserAnswerDAO;
import ru.testbest.persistence.mapper.UserAnswerMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserAnswerDAOImpl implements UserAnswerDAO {

    @Autowired
    private QuestionDAO questionDAO;

    JdbcTemplate jdbcTemplate;

    private final String SQL_INSERT_USER_ANSWER = "insert into testdb.user_answer (user_id, question_id, answer_id, answer_text, point) values(?,?,?,?,?)";
    private final String SQL_FIND_USER_ANSWER = "select * from testdb.user_answer where id = ?";
    private final String SQL_GET_ALL = "select * from testdb.user_answer";
    private final String SQL_DELETE_USER_ANSWER = "delete from testdb.user_answer where id = ?";
    private final String SQL_FIND_ANSWER_QUESTION = "select * from testdb.user_answer where user_id = ? and question_id = ?";
    private final String SQL_FIND_ANSWER_USER = "select * from testdb.user_answer where user_id = ?";

    @Autowired
    public UserAnswerDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserAnswer getUserAnswerById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_USER_ANSWER, new Object[]{id}, new UserAnswerMapper());
        } catch (
                EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<UserAnswer> getAllUsersAnswers() {
        return jdbcTemplate.query(SQL_GET_ALL, new UserAnswerMapper());
    }

    @Override
    public List<UserAnswer> getAllUserAnswers(Integer user_id) {
        List<UserAnswer> userAnswers = jdbcTemplate.query(SQL_FIND_ANSWER_USER, new Object[]{user_id}, new UserAnswerMapper());

        for (UserAnswer userAnswer : userAnswers
        ) {
            Question question = questionDAO.getQuestionByIdNoDetails(userAnswer.getQuestion_id());
            userAnswer.setQuestion(question);
        }

        return userAnswers;
    }

    @Override
    public boolean deleteUserAnswer(UserAnswer userAnswer) {
        return jdbcTemplate.update(SQL_DELETE_USER_ANSWER, userAnswer.getId()) > 0;
    }

    @Override
    public boolean updateUserAnswer(UserAnswer userAnswer) {
        return false;
    }

    @Override
    public boolean createUserAnswer(UserAnswer userAnswer) {
        return jdbcTemplate.update(SQL_INSERT_USER_ANSWER,
                userAnswer.getUser_id(),
                userAnswer.getQuestion_id(),
                userAnswer.getAnswer_id(),
                userAnswer.getAnswer_text(),
                userAnswer.getPoint()) > 0;
    }

    @Override
    public boolean validationCheckQuestion(Integer user_id, Integer question_id) {
        try {
            jdbcTemplate.queryForObject(SQL_FIND_ANSWER_QUESTION, new Object[]{user_id, question_id}, new UserAnswerMapper());
        } catch (
                EmptyResultDataAccessException ex) {
            return false;
        }
        return true;
    }

}