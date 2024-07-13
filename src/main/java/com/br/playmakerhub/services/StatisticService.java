package com.br.playmakerhub.services;

import com.br.playmakerhub.models.Statistics;
import com.br.playmakerhub.models.StatisticsHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

    public StatisticsHistory convertStatisticsToStatisticsHistory(Statistics statistics) {
        StatisticsHistory history = new StatisticsHistory();
        history.setMatches(statistics.getMatches());
        history.setAssists(statistics.getAssists());
        history.setGoals(statistics.getGoals());
        history.setRedCards(statistics.getRedCards());
        history.setYellowCards(statistics.getYellowCards());
       return history;
    }

//    public List<StatisticsHistory> somaStatisticsToStatisticsHistory(List<Statistics> statistics) {
//        StatisticsHistory history = new StatisticsHistory();
//        for (Statistics stats : statistics) {
//
//        }
//        history.setMatches(statistics.getMatches());
//        history.setAssists(statistics.getAssists());
//        history.setGoals(statistics.getGoals());
//        history.setRedCards(statistics.getRedCards());
//        history.setYellowCards(statistics.getYellowCards());
//        return history;
//    }
}
