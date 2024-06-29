package com.br.playmakerhub.services;

import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.repositories.CoachRepository;
import com.br.playmakerhub.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachService {

    @Autowired
    private CoachRepository repository;

    @Autowired
    private PlayerRepository playerRepository;

    public List<Coach> getAllCoaches() {
        return repository.findAll();
    }

    public Coach getCoachById(String id) {
        Optional<Coach> coach = repository.findById(id);
        return coach.orElse(null);
    }
    public Coach createCoach(Coach coach) {
        if (coach == null) {
            throw new IllegalArgumentException("Coach cannot be null");
        }
        return repository.save(coach);
    }

    public Coach addPlayerToCoach(String coachId, Player player) {
        Optional<Coach> coachOpt = repository.findById(coachId);

        if (coachOpt.isPresent()) {
            Coach coach = coachOpt.get();

            Player savedPlayer = playerRepository.save(player);

            List<Player> players = coach.getPlayers();
            players.add(savedPlayer);
            coach.setPlayers(players);

            return repository.save(coach);
        } else {
            throw new IllegalArgumentException("Coach not found");
        }
    }
}
