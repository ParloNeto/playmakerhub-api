package com.br.playmakerhub.services;

import com.br.playmakerhub.dto.CoachDTO;
import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.mapper.CoachMapper;
import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.repositories.CoachRepository;
import com.br.playmakerhub.repositories.PlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachService {

    private static final Logger logger = LogManager.getLogger(PlayerService.class);


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
    public Coach createCoach(CoachDTO coachDTO) {
        if (coachDTO == null) {
            throw new IllegalArgumentException("Coach cannot be null");
        }

        Coach coach = CoachMapper.INSTANCE.coachDtoToCoach(coachDTO);

        return repository.save(coach);
    }


    public void deleteCoach(String id) {
        Coach coach = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "No technician found with this ID"));
        repository.delete(coach);
    }
}
