package com.br.playmakerhub.services;

import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.models.Season;
import com.br.playmakerhub.repositories.SeasonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeasonService {

    private static final Logger logger = LogManager.getLogger(PlayerService.class);
    
    @Autowired
    private SeasonRepository repository;
    

    public List<Season> getAllSeasons() {
        return repository.findAll();
    }

    public Season getSeasonById(String id) {
        Optional<Season> season = repository.findById(id);
        return season.orElse(null);
    }
    public Season createSeason(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }
        return repository.save(season);
    }


    public void deleteSeason(String id) {
        Season season = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "No season found with this ID"));
        repository.delete(season);
    }
}
