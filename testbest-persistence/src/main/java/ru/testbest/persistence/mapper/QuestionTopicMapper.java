package ru.testbest.persistence.mapper;

import ru.testbest.dto.QuestionTopic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionTopicMapper implements RowMapper<QuestionTopic> {

    public QuestionTopic mapRow(ResultSet resultSet, int i) throws SQLException {

        QuestionTopic questionTopic = new QuestionTopic();
        questionTopic.setId(resultSet.getInt("id"));
        questionTopic.setName(resultSet.getString("name"));

        return questionTopic;
    }

}