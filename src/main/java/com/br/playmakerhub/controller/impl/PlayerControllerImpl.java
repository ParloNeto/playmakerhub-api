package com.br.playmakerhub.controller.impl;

import com.br.playmakerhub.controller.IPlayerController;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/players")
public class PlayerControllerImpl implements IPlayerController {

    @Autowired
    PlayerService service;


    @Override
    public ResponseEntity<Player> getPlayerById(String id) {
        Player player = service.getPlayerById(id);
        return ResponseEntity.ok().body(player);
    }

    @Override
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = service.getAllPlayers();
        return ResponseEntity.ok().body(players);
    }


    @Override
    public ResponseEntity<Player> createPlayer(Player player) throws IllegalAccessException {
        Player playersCreated = service.createPlayer(player);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(playersCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> deletePlayer(String id) {
        service.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
