package ru.testbest.persistence.dao.daoimpl;

import ru.testbest.dto.Question;
import ru.testbest.dto.QuestionAnswer;
import ru.testbest.persistence.dao.QuestionDAO;
import ru.testbest.persistence.mapper.QuestionMapper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuestionDAOImpl implements QuestionDAO {

    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_QUESTION = "select * from testdb.question where id = ?";
    private final String SQL_GET_ALL = "select * from testdb.question";
    private final String SQL_FIND_QUESTION_BY_TOPIC_ID = "select * from testdb.question where question_topic_id = ?";
    private final String SQL_DELETE_QUESTION = "delete from testdb.question where id = ?";
    private final String SQL_UPDATE_QUESTION = "update testdb.question set text = ?, question_topic_id = ?, question_type_id  = ? where id = ?";
    private final String SQL_INSERT_QUESTION = "insert into testdb.question(text, question_topic_id, question_type_id) values(?,?,?)";
    private final String SQL_GET_ALL_JOIN = "select q.id, q.text, q.question_topic_id, q.question_type_id, a.id as 'answer_id', answer_text, a.point, a.question_id from testdb.question q left join testdb.question_answer a on q.id = a.question_id";
    private final String SQL_GET_JOIN_QUESTION = "select q.id, q.text, q.question_topic_id, q.question_type_id, a.id as 'answer_id', answer_text, a.point, a.question_id from testdb.question q left join testdb.question_answer a on q.id = a.question_id where q.id = ?";

    @Autowired
    public QuestionDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Question getQuestionById(Integer s_id) {
        try {
            return jdbcTemplate.query(SQL_GET_JOIN_QUESTION, new Object[]{s_id},
                    rs -> {
                        Map<Integer, Question> map = new HashMap<>();
                        Question questions;
                        while (rs.next()) {
                            Integer id = rs.getInt("id");
                            questions = map.get(id);
                            if (questions == null) {
                                questions = new Question();
                                questions.setId(id);
                                questions.setText(rs.getString("text"));
                                questions.setQuestion_topic_id(rs.getInt("question_topic_id"));
                                questions.setQuestion_type_id(rs.getInt("question_type_id"));
                                questions.setQuestionAnswers(new HashSet<>());
                                map.put(id, questions);
                            }
                            Integer answer_id = rs.getInt("answer_id");
                            if (answer_id > 0) {
                                QuestionAnswer questionAnswer = new QuestionAnswer();
                                questionAnswer.setId(answer_id);
                                questionAnswer.setQuestion_id(id);
                                questionAnswer.setAnswer_text(rs.getString("answer_text"));
//                            questionAnswer.setPoint(rs.getInt("point"));
                                questions.addQuestionAnswers(questionAnswer);
                            }
                        }
                        return map.get(s_id);
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Question getQuestionByIdNoDetails(Integer s_id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_QUESTION, new Object[]{s_id}, new QuestionMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Question> getAllQuestionsByTopicId(Integer id) {
        return jdbcTemplate.query(SQL_FIND_QUESTION_BY_TOPIC_ID, new Object[]{id}, new QuestionMapper());
    }

    @Override
    public List<Question> getAllQuestions() {
        return jdbcTemplate.query(SQL_GET_ALL_JOIN,
                new QuestionMapper());
    }

    @Override
    public boolean deleteQuestion(Question question) {
        return jdbcTemplate.update(SQL_DELETE_QUESTION, question.getId()) > 0;
    }

    @Override
    public boolean updateQuestion(Question question) {
        return jdbcTemplate.update(SQL_UPDATE_QUESTION, question.getText(), question.getQuestion_topic_id(), question.getQuestion_type_id(),
                question.getId()) > 0;
    }

    @Override
    public boolean createQuestion(Question question) {
        if (question.getText() != null && question.getQuestion_type_id() != null && question.getQuestion_type_id() != null) {
            return jdbcTemplate.update(SQL_INSERT_QUESTION, question.getText(), question.getQuestion_topic_id(), question.getQuestion_type_id()) > 0;
        }
        return false;
    }

}

//    @Override
//    public List<Question> getAllQuestions() {
//        return jdbcTemplate.query(SQL_GET_ALL_JOIN,
//                rs -> {
//                    Map<Integer, Question> map = new HashMap<>();
//                    Question questions;
//                    while (rs.next()) {
//                        Integer id = rs.getInt("id");
//                        questions = map.get(id);
//                        if (questions == null) {
//                            questions = new Question();
//                            questions.setId(id);
//                            questions.setText(rs.getString("text"));
//                            questions.setQuestion_topic_id(rs.getInt("question_topic_id"));
//                            questions.setQuestion_type_id(rs.getInt("question_type_id"));
//                            questions.setQuestionAnswers(new HashSet<>());
//                            map.put(id, questions);
//                        }
//                        Integer answer_id = rs.getInt("answer_id");
//                        if (answer_id > 0) {
//                            QuestionAnswer questionAnswer = new QuestionAnswer();
//                            questionAnswer.setId(answer_id);
//                            questionAnswer.setQuestion_id(id);
//                            questionAnswer.setAnswer_text(rs.getString("answer_text"));
//                            questionAnswer.setPoint(rs.getInt("point"));
//                            questions.addQuestionAnswers(questionAnswer);
//                        }
//                    }
//                    return new ArrayList<>(map.values());
//                });
//    }