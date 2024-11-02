package com.br.playmakerhub.services;

import com.br.playmakerhub.dto.PlayerStatsDTO;
import com.br.playmakerhub.mapper.PlayerMapper;
import com.br.playmakerhub.models.*;
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

    public CareerHistory calculateCareerHistory(List<Season> seasons, CareerHistory careerHistory) {
        int totalGames = seasons.stream().mapToInt(Season::getGames).sum();
        int totalWins = seasons.stream().mapToInt(Season::getWins).sum();
        int totalDraws = seasons.stream().mapToInt(Season::getDraws).sum();
        int totalLosses = seasons.stream().mapToInt(Season::getLosses).sum();
        int totalGoalsConceded = seasons.stream().mapToInt(Season::getGoalsConceded).sum();
        int totalGoalsScored = seasons.stream().mapToInt(Season::getGoalsScored).sum();

        careerHistory.setGames(totalGames);
        careerHistory.setWins(totalWins);
        careerHistory.setDraws(totalDraws);
        careerHistory.setLosses(totalLosses);
        careerHistory.setGoalsConceded(totalGoalsConceded);
        careerHistory.setGoalsScored(totalGoalsScored);

        return careerHistory;
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
