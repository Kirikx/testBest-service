package ru.testbest.service;

import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TestStatistics {

    Long getAllRegisteredUsersCount();

    Long getAllUserTestsCount(UUID testId);

    Long getAllCompleteUserTestsCount(UUID testId);

    Long getAllPassedUserTestsCount(UUID testId);
}
