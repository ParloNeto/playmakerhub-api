package com.br.playmakerhub.mocks;

import com.br.playmakerhub.dto.SeasonDTO;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Season;
import com.br.playmakerhub.models.Team;

import java.util.ArrayList;
import java.util.List;

import static com.br.playmakerhub.mocks.MockStatistics.mockStatisticsHistory;
import static com.br.playmakerhub.mocks.MockStatistics.mockStatisticsList;

public class MockSeason {

    private final MockPlayer mockPlayer = new MockPlayer();

    public Season mockEntity() {
        Season season = new Season();
        season.setId("6734ad4f4da1c43f44e746dd");
        season.setSeasonName("temporada-24-25");
        season.setGames(43);
        season.setWins(24);
        season.setDraws(8);
        season.setLosses(11);
        season.setGoalsScored(98);
        season.setGoalsConceded(68);
        season.setPlayers(mockPlayer.mockListEntity());
        return season;
    }

    public SeasonDTO mockEntityDTO() {
        SeasonDTO season = new SeasonDTO();
        season.setSeasonName("temporada-24-25");
        season.setGames(43);
        season.setWins(24);
        season.setDraws(8);
        season.setLosses(11);
        season.setGoalsScored(98);
        season.setGoalsConceded(68);
        return season;
    }

    public List<Season> mockListEntity() {
        List<Season> list = new ArrayList<>();

        Season seasonOne = mockEntity();
        list.add(seasonOne);

        Season seasonTwo = new Season();
        seasonTwo.setId("6734ad4f4da1c43f44e746df");
        seasonTwo.setSeasonName("temporada-23-24");
        seasonTwo.setGames(38);
        seasonTwo.setWins(20);
        seasonTwo.setDraws(10);
        seasonTwo.setLosses(8);
        seasonTwo.setGoalsScored(85);
        seasonTwo.setGoalsConceded(55);
        seasonTwo.setPlayers(mockPlayer.mockListEntity());

        list.add(seasonTwo);

        Season seasonThree = new Season();
        seasonThree.setId("6734ad4f4da1c43f44e746df");
        seasonThree.setSeasonName("temporada-22-23");
        seasonThree.setGames(80);
        seasonThree.setWins(50);
        seasonThree.setDraws(10);
        seasonThree.setLosses(20);
        seasonThree.setGoalsScored(96);
        seasonThree.setGoalsConceded(64);
        seasonThree.setPlayers(mockPlayer.mockListEntity());

        list.add(seasonThree);

        return list;
    }
}
