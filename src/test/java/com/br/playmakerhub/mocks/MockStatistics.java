package com.br.playmakerhub.mocks;

import com.br.playmakerhub.dto.CoachDTO;
import com.br.playmakerhub.models.CareerHistory;
import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.models.Statistics;
import com.br.playmakerhub.models.StatisticsHistory;

import java.util.ArrayList;
import java.util.List;

public class MockStatistics {



    public static List<Statistics> mockStatisticsList() {
        List<Statistics> statistics = new ArrayList<>();

        Statistics statsOne = new Statistics();
        statsOne.setSeason("temporada-24-25");
        statsOne.setMatches(43);
        statsOne.setGoals(20);
        statsOne.setAssists(10);
        statsOne.setYellowCards(5);
        statsOne.setRedCards(1);

        statistics.add(statsOne);

        Statistics statsTwo = new Statistics();
        statsTwo.setSeason("temporada-23-24");
        statsTwo.setMatches(43);
        statsTwo.setGoals(20);
        statsTwo.setAssists(10);
        statsTwo.setYellowCards(5);
        statsTwo.setRedCards(1);

        statistics.add(statsTwo);

        Statistics statsThree = new Statistics();
        statsThree.setSeason("temporada-22-23");
        statsThree.setMatches(64);
        statsThree.setGoals(16);
        statsThree.setAssists(6);
        statsThree.setYellowCards(4);
        statsThree.setRedCards(0);

        statistics.add(statsThree);

        return statistics;
    }

    public static StatisticsHistory mockStatisticsHistory() {
        StatisticsHistory history = new StatisticsHistory();
        history.setMatches(43);
        history.setGoals(20);
        history.setAssists(10);
        history.setYellowCards(5);
        history.setRedCards(1);
        return history;
    }

    public static CareerHistory mockCareerHistory() {
        return new CareerHistory(100, 60, 10, 30, 75, 100, null);
    }
}
