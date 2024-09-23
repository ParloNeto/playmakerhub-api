package com.br.playmakerhub.controller.impl;

import com.br.playmakerhub.controller.IPlayerController;
import com.br.playmakerhub.dto.PlayerDTO;
import com.br.playmakerhub.mapper.PlayerMapper;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<Player> createPlayer(PlayerDTO playerDto) {

        Player playerCreated = service.createPlayer(playerDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(playerCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(playerCreated);
    }

    @Override
    public ResponseEntity<Player> updatePlayer(Player player) {
        service.updatePlayer(player);
        return ResponseEntity.ok().body(player);
    }

    @Override
    public ResponseEntity<Void> deletePlayer(String id) {
        service.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
