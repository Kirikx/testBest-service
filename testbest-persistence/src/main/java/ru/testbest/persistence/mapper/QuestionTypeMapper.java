package ru.testbest.persistence.mapper;

import ru.testbest.dto.QuestionType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionTypeMapper implements RowMapper<QuestionType> {

    public QuestionType mapRow(ResultSet resultSet, int i) throws SQLException {

        QuestionType questionType = new QuestionType();
        questionType.setId(resultSet.getInt("id"));
        questionType.setName(resultSet.getString("name"));

        return questionType;
    }

}
