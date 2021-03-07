package ru.testbest.persistence.dao;

import ru.testbest.dto.Statistic;

public interface StatisticDAO {

    Statistic getCountUserRegistration();

    Statistic getCountUserCheckTestings();

    Statistic getCountUserAnswerAll();

    Statistic getCountUserAnswerAllTrue();


    Statistic getUserProgress(Integer id);

    Statistic getCountUsersProgressWorse(Integer id);

    Statistic getCountUsersProgressBetter(Integer id);

}
