package ru.testbest.persistence.dao.stat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.testbest.persistence.entity.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserStatsDao extends JpaRepository<User, UUID> {

// сколько всего пользователей зарегистрировано в системе
    @Query(value = "SELECT count(u.id) AS Stat FROM `user` u " +
            "INNER JOIN `user_role` ur ON u.id = ur.user_id " +
            "INNER JOIN `role` r ON ur.role_id = r.id " +
            "WHERE r.name = 'ROLE_USER'", nativeQuery = true)
    Optional<Long> findAllRegisteredUsersCount();

// сколько пользователей прошли тестирование (== сколько прошли конкретный тест)
    @Query(value = "SELECT count(id) FROM `user_test` " +
            "WHERE finished IS NOT NULL AND test_id = :id", nativeQuery = true)
    Optional<Long> findAllUserTestsCount(@Param("id") UUID testId);

// сколько пользователей ответили на все вопросы конкретного теста
    @Query(value = "SELECT count(stat.user_id) FROM (SELECT ut.user_id, " +
            "utq.user_test_id, count(utq.question_id) AS `questions_answered` " +
            "FROM `user_test` ut INNER JOIN `user_test_question` utq " +
            "ON ut.id = utq.user_test_id WHERE ut.test_id = :id " +
            "GROUP BY utq.user_test_id HAVING questions_answered = " +
            "(SELECT count(q.id) FROM `test` t INNER JOIN `chapter` c " +
            "ON t.id = c.test_id INNER JOIN `question_chapter` qc " +
            "ON c.id = qc.chapter_id INNER JOIN `question` q ON qc.question_id = q.id " +
            "WHERE t.id = :id GROUP BY t.id)) AS `stat`", nativeQuery = true)
    Optional<Long> findAllCompleteUserTestsCount(@Param("id") UUID testId);

// сколько пользователей ответили на все вопросы теста правильно
    @Query(value = "SELECT count(stat.user_id) FROM (SELECT ut.user_id, " +
            "utq.user_test_id, count(utq.question_id) AS `questions_answered` " +
            "FROM `user_test` ut INNER JOIN `user_test_question` utq " +
            "ON ut.id = utq.user_test_id WHERE ut.test_id = :id AND utq.correct = 1 " +
            "GROUP BY utq.user_test_id HAVING questions_answered = " +
            "(SELECT count(q.id) FROM `test` t INNER JOIN `chapter` c " +
            "ON t.id = c.test_id INNER JOIN `question_chapter` qc " +
            "ON c.id = qc.chapter_id INNER JOIN `question` q ON qc.question_id = q.id " +
            "WHERE t.id = :id GROUP BY t.id)) AS `stat`", nativeQuery = true)
    Optional<Long> findAllPassedUserTestsCount(@Param("id") UUID testId);
}
