package com.br.playmakerhub.controller;

import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.services.CoachService;
import com.br.playmakerhub.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/players")

public class PlayerController {

    @Autowired
    PlayerService service;

    @Autowired
    CoachService coachService;

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = service.getAllPlayers();

        return ResponseEntity.ok().body(players);
    }
    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player playersCreated = service.createPlayer(player);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(playersCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/addToCoach/{coachId}")
    public ResponseEntity<Coach> addPlayerToCoach(@PathVariable String coachId, @RequestBody Player player) {
        Coach updatedCoach = coachService.addPlayerToCoach(coachId, player);
        return ResponseEntity.ok(updatedCoach);
    }
}
