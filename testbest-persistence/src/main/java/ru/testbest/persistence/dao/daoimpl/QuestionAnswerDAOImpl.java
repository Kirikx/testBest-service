package ru.testbest.persistence.dao.daoimpl;

import ru.testbest.dto.QuestionAnswer;
import ru.testbest.persistence.dao.QuestionAnswerDAO;
import ru.testbest.persistence.mapper.QuestionAnswerMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuestionAnswerDAOImpl implements QuestionAnswerDAO {

  JdbcTemplate jdbcTemplate;

  private final String SQL_FIND_QUESTION = "select * from testdb.question_answer where id = ?";
  private final String SQL_DELETE_QUESTION = "delete from testdb.question_answer where id = ?";
  private final String SQL_UPDATE_QUESTION = "update testdb.question_answer set answer_text = ?, question_id = ?, point  = ? where id = ?";
  private final String SQL_GET_ALL = "select * from testdb.question_answer";
  private final String SQL_GET_ALL_BY_QUESTION_ID = "select * from testdb.question_answer where question_id = ?";
  private final String SQL_INSERT_QUESTION = "insert into testdb.question_answer(answer_text, question_id, point) values(?,?,?)";

  @Autowired
  public QuestionAnswerDAOImpl(DataSource dataSource) {
    jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public QuestionAnswer getQuestionAnswerById(Integer id) {
    try {
      return jdbcTemplate.queryForObject(SQL_FIND_QUESTION,
          new Object[]{id},
          new QuestionAnswerMapper());
    } catch (
        EmptyResultDataAccessException ex) {
      return null;
    }
  }

  @Override
  public List<QuestionAnswer> getAllQuestionAnswers() {
    return jdbcTemplate.query(SQL_GET_ALL, new QuestionAnswerMapper());
  }

  @Override
  public List<QuestionAnswer> getAllQuestionAnswersByQuestionId(Integer question_id) {
    return jdbcTemplate
        .query(SQL_GET_ALL_BY_QUESTION_ID, new Object[]{question_id}, new QuestionAnswerMapper());
  }

  @Override
  public boolean deleteQuestionAnswer(QuestionAnswer questionAnswer) {
    return jdbcTemplate.update(SQL_DELETE_QUESTION, questionAnswer.getId()) > 0;
  }

  @Override
  public boolean updateQuestionAnswer(QuestionAnswer questionAnswer) {
    return jdbcTemplate.update(SQL_UPDATE_QUESTION, questionAnswer.getAnswer_text(),
        questionAnswer.getQuestion_id(), questionAnswer.getPoint(),
        questionAnswer.getId()) > 0;
  }

  @Override
  public boolean createQuestionAnswer(QuestionAnswer questionAnswer) {
    if (questionAnswer.getAnswer_text() != null && questionAnswer.getQuestion_id() != null
        && questionAnswer.getPoint() != null) {
      return jdbcTemplate.update(SQL_INSERT_QUESTION, questionAnswer.getAnswer_text(),
          questionAnswer.getQuestion_id(), questionAnswer.getPoint()) > 0;
    }
    return false;
  }

  @Override
  public Integer validationAnswerInput(Integer question_id, String answer_user) {
    List<QuestionAnswer> questionAnswerList = getAllQuestionAnswersByQuestionId(question_id);
    for (QuestionAnswer answer : questionAnswerList) {
      if (answer.getAnswer_text().equalsIgnoreCase(answer_user.toLowerCase())) {
        return 1;
      }
    }
    return 0;
  }

}