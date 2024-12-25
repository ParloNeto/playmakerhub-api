package com.br.playmakerhub.services;

import com.br.playmakerhub.dto.SeasonDTO;
import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.exceptions.season.SeasonNotFoundException;
import com.br.playmakerhub.mapper.SeasonMapper;
import com.br.playmakerhub.models.Career;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Season;
import com.br.playmakerhub.models.Statistics;
import com.br.playmakerhub.repositories.CareerRepository;
import com.br.playmakerhub.repositories.SeasonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeasonService {

    private static final Logger logger = LogManager.getLogger(PlayerService.class);

    @Autowired
    private SeasonRepository repository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    @Lazy
    private CareerService careerService;

    @Autowired
    private PlayerService playerService;
    

    public List<Season> getAllSeasons() {
        return repository.findAll();
    }




    public Season getSeasonById(String id) {
        return repository.findById(id).orElseThrow(SeasonNotFoundException::new);
    }
    public Season createSeason(SeasonDTO seasonDTO) {
        if (seasonDTO == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }

        Season season = SeasonMapper.INSTANCE.seasonDtoToSeason(seasonDTO);
        return repository.save(season);
    }



    public Season updateStatisticsSeason(String seasonId, Season seasonUpdated) {
        Season seasonPersisted = getSeasonById(seasonId);

        seasonPersisted.setGames(seasonUpdated.getGames());
        seasonPersisted.setWins(seasonUpdated.getWins());
        seasonPersisted.setDraws(seasonUpdated.getDraws());
        seasonPersisted.setLosses(seasonUpdated.getLosses());
        seasonPersisted.setGoalsConceded(seasonUpdated.getGoalsConceded());
        seasonPersisted.setGoalsScored(seasonUpdated.getGoalsScored());

        Season savedSeason = repository.save(seasonPersisted);
        List<Career> careers = findCareersBySeasonId(seasonId);
        careers.forEach(career -> careerService.updateCareerHistoryByFirstSeason(career.getId()));

        return savedSeason;
    }

    public Season setPlayersToSeason(String id, List<Player> player) {
        Season season = repository.findById(id).orElseThrow(SeasonNotFoundException::new);

        season.setPlayers(player);

        return repository.save(season);
    }

    public Season removePlayerFromSeason(String seasonId, String playerId) {
        Season season = repository.findById(seasonId).orElseThrow(SeasonNotFoundException::new);

        Optional<Player> playerFiltered = season.getPlayers().stream()
                .filter(p -> p.getId().equalsIgnoreCase(playerId))
                .findFirst();

        if (playerFiltered.isPresent()) {
            Player player = playerFiltered.get();

            Optional<Statistics> seasonStats = player.getStatisticsBySeasons().stream()
                    .filter(stats -> stats.getSeason().equalsIgnoreCase(season.getSeasonName()))
                    .findFirst();

            seasonStats.ifPresent(stats -> player.getStatisticsBySeasons().remove(stats));

            playerService.updateStatisticsHistory(player, player.getStatisticsBySeasons());
        }

        List<Player> updatedPlayers = season.getPlayers().stream()
                .filter(player -> !player.getId().equals(playerId))
                .collect(Collectors.toList());
        season.setPlayers(updatedPlayers);

        return repository.save(season);
    }

    public void deleteSeason(String id) {
        Season season = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "No season found with this ID"));
        repository.delete(season);
    }

    private List<Career> findCareersBySeasonId(String seasonId) {
        return careerRepository.findAll().stream()
                .filter(career -> career.getSeasons().stream().anyMatch(season -> season.getId().equals(seasonId)))
                .collect(Collectors.toList());
    }
}
