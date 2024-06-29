package com.br.playmakerhub.services;

import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository repository;

    public List<Player> getAllPlayers() {
        return repository.findAll();
    }

    public Player createPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        return repository.save(player);
    }

}
