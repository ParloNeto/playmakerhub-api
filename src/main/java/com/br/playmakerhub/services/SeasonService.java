package com.br.playmakerhub.services;

import com.br.playmakerhub.dto.SeasonDTO;
import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.exceptions.season.SeasonNotFoundException;
import com.br.playmakerhub.mapper.SeasonMapper;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Season;
import com.br.playmakerhub.repositories.SeasonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeasonService {

    private static final Logger logger = LogManager.getLogger(PlayerService.class);
    
    @Autowired
    private SeasonRepository repository;

    @Autowired
    @Lazy
    private CareerService careerService;
    

    public List<Season> getAllSeasons() {
        return repository.findAll();
    }

    public List<Season> getAllSeasonsByCareerId(String careerId) {
        List<Season> seasons = careerService.getCareerById(careerId).getSeasons();

        return seasons;
    }


    public Season getSeasonById(String id) {
        Optional<Season> season = repository.findById(id);
        return season.orElse(null);
    }
    public Season createSeason(SeasonDTO seasonDTO) {
        if (seasonDTO == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }

        Season season = SeasonMapper.INSTANCE.seasonDtoToSeason(seasonDTO);
        return repository.save(season);
    }

    public Season setPlayersToSeason(String id, List<Player> player) {
        Season season = repository.findById(id).orElseThrow(SeasonNotFoundException::new);

        season.setPlayers(player);

        return repository.save(season);
    }

    public void deleteSeason(String id) {
        Season season = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "No season found with this ID"));
        repository.delete(season);
    }
}
