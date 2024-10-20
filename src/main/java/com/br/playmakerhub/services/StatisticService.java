package com.br.playmakerhub.services;

import com.br.playmakerhub.dto.PlayerStatsDTO;
import com.br.playmakerhub.mapper.PlayerMapper;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Statistics;
import com.br.playmakerhub.models.StatisticsHistory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<PlayerStatsDTO> sortPlayerByGoals(List<Player> players) {
        List<PlayerStatsDTO> playerStatsDTOS = PlayerMapper.INSTANCE.convertListPlayerToListStatsDTO(players);

        return playerStatsDTOS.stream().sorted((a, b) -> {

            int goalsA = a.getGoals();
            int goalsB = b.getGoals();

            return Integer.compare(goalsB, goalsA);
        }).collect(Collectors.toList());
    }

    public List<PlayerStatsDTO> sortPlayerByAssists(List<Player> players) {
        List<PlayerStatsDTO> playerStatsDTOS = PlayerMapper.INSTANCE.convertListPlayerToListStatsDTO(players);

        return playerStatsDTOS.stream().sorted((a, b) -> {
            int  assistsA = a.getAssists();

            int assistsB = b.getAssists();

            return Integer.compare(assistsB, assistsA);
        }).collect(Collectors.toList());
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
