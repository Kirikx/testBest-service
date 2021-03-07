package ru.testbest.persistence.mapper;

import ru.testbest.dto.Statistic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticMapper implements RowMapper<Statistic> {

    public Statistic mapRow(ResultSet resultSet, int i) throws SQLException {

        Statistic statistic = new Statistic();
        statistic.setValue(resultSet.getInt("value"));

        return statistic;
    }

}