package com.br.playmakerhub.services;

import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.models.League;
import com.br.playmakerhub.models.Team;
import com.br.playmakerhub.repositories.LeagueRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class LeagueService {

    @Autowired
    LeagueRepository repository;

    private static final Logger logger = LogManager.getLogger(LeagueService.class);

    public League findLeagueById(String id) {
        logger.info("findLeagueById() is called");
        League league = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("League not found"));

        sortTeamsByName(league.getTeams());

        return league;
    }

    public List<League> findAllLeagues() {
        logger.info("findAllLeagues() is called");
        List<League> leagues = repository.findAll();

        leagues.forEach((league) -> {
            sortTeamsByName(league.getTeams());
        });

        return leagues;
    }

    public League createLeague(League league) {
        logger.info("createLeague() is called");
        if (league == null) return null;

        return repository.save(league);
    }

    public League addTeamToExistingLeague(String id, Team team) {
        logger.info("addTeamToExistingLeague() is called");
        League league = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("League not found"));

        if (team == null) throw new ObjectNotFoundException("Team is null");

        league.getTeams().add(team);

        logger.info("addTeamToExistingLeague() is finished");
        return repository.save(league);
    }

    public League updateLeague(League leagueUpdated) {
        logger.info("updateLeague() is called");
        League league = repository.findById(leagueUpdated.getId()).orElseThrow(() -> new ObjectNotFoundException("League not found"));
        league.setName(leagueUpdated.getName());
        league.setTeams(leagueUpdated.getTeams());
        logger.info("updateLeague() is finished");
        return repository.save(league);
    }

    public void deleteLeague(String id) {
        logger.info("deleteLeague() is called");
        League league = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("League not found"));
        repository.delete(league);
        logger.info("deleteLeague() is finished");
    }

    private void sortTeamsByName(List<Team> teams) {
        teams.sort(Comparator.comparing(Team::getName));
    }
}
