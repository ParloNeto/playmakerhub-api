package com.br.playmakerhub.controller.impl;

import com.br.playmakerhub.controller.ILeagueController;
import com.br.playmakerhub.models.League;
import com.br.playmakerhub.models.Team;
import com.br.playmakerhub.services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("leagues")
public class LeagueControllerImpl implements ILeagueController {

    @Autowired
    LeagueService service;

    @Override
    public ResponseEntity<League> getLeagueById(@PathVariable String id) {
        League league = service.findLeagueById(id);
        return ResponseEntity.ok().body(league);
    }

    @Override
    public ResponseEntity<List<League>> getAllLeagues() {
        List<League> leagues = service.findAllLeagues();
        return ResponseEntity.ok().body(leagues);
    }

    @Override
    public ResponseEntity<League> createLeague(@RequestBody League league) {
        League leagueCreated = service.createLeague(league);
        return ResponseEntity.status(HttpStatus.CREATED).body(leagueCreated);
    }

    @Override
    public ResponseEntity<League> updateLeague(@RequestBody League leagueUpdated) {
        League league = service.updateLeague(leagueUpdated);
        return ResponseEntity.ok().body(league);
    }

    @Override
    public ResponseEntity<League> updateLeague(@PathVariable String id, @RequestBody Team team) {
        League league = service.addTeamToExistingLeague(id, team);
        return ResponseEntity.ok().body(league);
    }

    @Override
    public ResponseEntity<Void> deleteLeague(@PathVariable String id) {
        service.deleteLeague(id);
        return ResponseEntity.noContent().build();
    }

}
