package ru.testbest.service.impl.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.testbest.persistence.dao.stat.UserStatsDao;
import ru.testbest.service.TestStatistics;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestStatisticsImpl implements TestStatistics {

    private UserStatsDao userStatsDao;

    @Override
    public Long getAllRegisteredUsersCount() {
        return userStatsDao.findAllRegisteredUsersCount().orElse(null);
    }

    @Override
    public Long getAllUserTestsCount(UUID testId) {
        return userStatsDao.findAllUserTestsCount(testId).orElse(null);
    }

    @Override
    public Long getAllCompleteUserTestsCount(UUID testId) {
        return userStatsDao.findAllCompleteUserTestsCount(testId).orElse(null);
    }

    @Override
    public Long getAllPassedUserTestsCount(UUID testId) {
        return userStatsDao.findAllPassedUserTestsCount(testId).orElse(null);
    }
}
