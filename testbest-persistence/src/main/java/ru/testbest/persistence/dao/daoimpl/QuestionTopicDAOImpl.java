package ru.testbest.persistence.dao.daoimpl;

import ru.testbest.dto.QuestionTopic;
import ru.testbest.persistence.dao.QuestionTopicDAO;
import ru.testbest.persistence.mapper.QuestionTopicMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QuestionTopicDAOImpl implements QuestionTopicDAO {

    JdbcTemplate jdbcTemplate;

    private final String SQL_FIND_QUESTION = "select * from testdb.question_topic where id = ?";
    private final String SQL_DELETE_QUESTION = "delete from testdb.question_topic where id = ?";
    private final String SQL_UPDATE_QUESTION = "update testdb.question_topic set name = ? where id = ?";
    private final String SQL_GET_ALL = "select * from testdb.question_topic";
    private final String SQL_INSERT_QUESTION = "insert into testdb.question_topic(name) values(?)";

    @Autowired
    public QuestionTopicDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public QuestionTopic getQuestionTopicById(Integer id) {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_QUESTION, new Object[]{id}, new QuestionTopicMapper());
        } catch (
                EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<QuestionTopic> getAllTopics() {
        return jdbcTemplate.query(SQL_GET_ALL, new QuestionTopicMapper());
    }

    @Override
    public boolean deleteTopic(QuestionTopic questionTopic) {
        return jdbcTemplate.update(SQL_DELETE_QUESTION, questionTopic.getId()) > 0;
    }

    @Override
    public boolean updateTopic(QuestionTopic questionTopic) {
        return jdbcTemplate.update(SQL_UPDATE_QUESTION, questionTopic.getName(), questionTopic.getId()) > 0;
    }

    @Override
    public boolean createTopic(QuestionTopic questionTopic) {
        if (questionTopic.getName() !=null) {
            return jdbcTemplate.update(SQL_INSERT_QUESTION, questionTopic.getName()) > 0;
        }
        return false;
    }

}
