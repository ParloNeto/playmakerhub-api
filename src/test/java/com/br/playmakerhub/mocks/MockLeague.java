package com.br.playmakerhub.mocks;

import com.br.playmakerhub.models.League;
import com.br.playmakerhub.models.Team;

import java.util.ArrayList;
import java.util.List;

public class MockLeague {



    public League mockEntity() {
        League league = new League();
        league.setId("66e0630de36afe058170dde6");
        league.setName("Premier League");
        league.setTeams(mockTeamList());

        return league;
    }

    public List<League> mockListEntity() {
        List<League> list = new ArrayList<League>();

        League leagueOne = new League();
        leagueOne.setId("66e0630de36afe058170dde6");
        leagueOne.setName("Premier League");
        leagueOne.setTeams(mockTeamList());

        list.add(leagueOne);

        return list;
    }

    public List<Team> mockTeamList() {
        List<Team> teams = new ArrayList<>();

        Team teamOne = new Team("Arsenal");
        teams.add(teamOne);

        Team teamTwo = new Team("Chelsea");
        teams.add(teamTwo);

        Team teamThree = new Team("Manchester United");
        teams.add(teamThree);

        return teams;
    }
}
