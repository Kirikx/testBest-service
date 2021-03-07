package ru.testbest.persistence.dao.daoimpl;

import ru.testbest.dto.Statistic;
import ru.testbest.persistence.dao.StatisticDAO;
import ru.testbest.persistence.mapper.StatisticMapper;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class StatisticDAOImpl implements StatisticDAO {

    JdbcTemplate jdbcTemplate;

    private final String SQL_COUNT_USER_REGISTRATION = "SELECT COUNT(*) as value FROM testdb.user";
    private final String SQL_COUNT_USER_CHECK_TESTING = "select count(DISTINCT user_id) as value from testdb.user_answer";
    private final String SQL_COUNT_USER_ANSWER_ALL = "select count(*) as value from testdb.user right join (select user_id, count(*) as count_answer from testdb.user_answer) ua on user.id = ua.user_id where count_answer = (select count(*) from testdb.question)";
    private final String SQL_COUNT_USER_ANSWER_ALL_TRUE = "select count(*) as value from testdb.user right join (select user_id, count(*) as count_answer from testdb.user_answer where point > 0) ua on user.id = ua.user_id where count_answer = (select count(*) from testdb.question)";
    private final String SQL_USER_PROGRESS = "select round(count(*) / (select count(*) from testdb.question) * 100) as value from testdb.user_answer ua where point > 0  and user_id = ?";
    private final String SQL_COUNT_USERS_PROGRESS_WORSE = "select count(*) as value from (select sum(point) as alluserspoint, user_id from user_answer group by user_id having alluserspoint < (select sum(point)from user_answer where user_id = ?)) as aui";
    private final String SQL_COUNT_USERS_PROGRESS_BETTER = "select count(*) as value from (select sum(point) as alluserspoint, user_id from user_answer group by user_id having alluserspoint > (select sum(point)from user_answer where user_id = ?)) as aui";


    @Autowired
    public StatisticDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Statistic getCountUserRegistration() {
        try {
            return jdbcTemplate.queryForObject(SQL_COUNT_USER_REGISTRATION, new StatisticMapper());
        } catch (
                EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Statistic getCountUserCheckTestings() {
        try {
            return jdbcTemplate.queryForObject(SQL_COUNT_USER_CHECK_TESTING, new StatisticMapper());
        } catch (
                EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Statistic getCountUserAnswerAll() {
        try {
            return jdbcTemplate.queryForObject(SQL_COUNT_USER_ANSWER_ALL, new StatisticMapper());
        } catch (
                EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Statistic getCountUserAnswerAllTrue() {
        try {
            return jdbcTemplate.queryForObject(SQL_COUNT_USER_ANSWER_ALL_TRUE, new StatisticMapper());
        } catch (
                EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Statistic getUserProgress(Integer id) {
        if (id != null) {
            try {
                return jdbcTemplate.queryForObject(SQL_USER_PROGRESS, new Object[]{id}, new StatisticMapper());
            } catch (
                    EmptyResultDataAccessException ex) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Statistic getCountUsersProgressWorse(Integer id) {
        if (id != null) {
            try {
                return jdbcTemplate.queryForObject(SQL_COUNT_USERS_PROGRESS_WORSE, new StatisticMapper(), new Object[]{id});
            } catch (
                    EmptyResultDataAccessException ex) {
                return null;
            }
        }
        return null;
    }

    @Override
    public Statistic getCountUsersProgressBetter(Integer id) {
        if (id != null) {
            try {
                return jdbcTemplate.queryForObject(SQL_COUNT_USERS_PROGRESS_BETTER, new StatisticMapper(), new Object[]{id});
            } catch (
                    EmptyResultDataAccessException ex) {
                return null;
            }
        }
        return null;
    }
}


