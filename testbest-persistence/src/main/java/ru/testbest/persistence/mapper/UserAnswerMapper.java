package ru.testbest.persistence.mapper;

import ru.testbest.dto.UserAnswer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAnswerMapper implements RowMapper<UserAnswer> {

    public UserAnswer mapRow (ResultSet resultSet, int i) throws SQLException {

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setId(resultSet.getInt("id"));
        userAnswer.setAnswer_text(resultSet.getString("answer_text"));
        userAnswer.setPoint(resultSet.getInt("point"));
        userAnswer.setQuestion_id(resultSet.getInt("question_id"));
        userAnswer.setAnswer_id(resultSet.getInt("answer_id"));
        userAnswer.setUser_id(resultSet.getInt("user_id"));

        return userAnswer;
    }
}
