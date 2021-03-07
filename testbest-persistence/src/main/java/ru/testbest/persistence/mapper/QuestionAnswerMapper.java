package ru.testbest.persistence.mapper;

import ru.testbest.dto.QuestionAnswer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionAnswerMapper implements RowMapper<QuestionAnswer> {

    public QuestionAnswer mapRow(ResultSet resultSet, int i) throws SQLException {

        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setId(resultSet.getInt("id"));
        questionAnswer.setAnswer_text(resultSet.getString("answer_text"));
        questionAnswer.setPoint(resultSet.getInt("point"));
        questionAnswer.setQuestion_id(resultSet.getInt("question_id"));

        return questionAnswer;
    }
}
