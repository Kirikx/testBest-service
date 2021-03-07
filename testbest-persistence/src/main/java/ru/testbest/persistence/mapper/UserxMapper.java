package ru.testbest.persistence.mapper;

import ru.testbest.dto.Userx;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserxMapper implements RowMapper<Userx> {

    public Userx mapRow(ResultSet resultSet, int i) throws SQLException {

        Userx user = new Userx();
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setActive(resultSet.getBoolean("active"));

        return user;
    }
}
