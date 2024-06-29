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
@RequestMapping(value = "/coaches")

public class CoachController {

    @Autowired
    CoachService service;

    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches() {
        List<Coach> coaches = service.getAllCoaches();

        return ResponseEntity.ok().body(coaches);
    }

    @GetMapping("/{coachId}")
    public ResponseEntity<Coach> getCoachById(@PathVariable String coachId) {
        Coach coach = service.getCoachById(coachId);

        return ResponseEntity.ok().body(coach);
    }
    @PostMapping
    public ResponseEntity<Coach> createCoach(@RequestBody Coach coach) {
        Coach coachCreated = service.createCoach(coach);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(coachCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


}
