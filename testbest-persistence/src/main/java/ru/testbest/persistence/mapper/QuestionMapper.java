package ru.testbest.persistence.mapper;

import ru.testbest.dto.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionMapper implements RowMapper<Question> {


    public Question mapRow(ResultSet resultSet, int i) throws SQLException {

        Question question = new Question();
        question.setId(resultSet.getInt("id"));
        question.setText(resultSet.getString("text"));
        question.setQuestion_topic_id(resultSet.getInt("question_topic_id"));
        question.setQuestion_type_id(resultSet.getInt("question_type_id"));

        return question;
    }

}