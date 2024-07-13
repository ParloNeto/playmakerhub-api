package com.br.playmakerhub.services;

import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.exceptions.SeasonAlreadyExistsException;
import com.br.playmakerhub.models.*;
import com.br.playmakerhub.repositories.CareerRepository;
import com.br.playmakerhub.repositories.PlayerRepository;
import com.br.playmakerhub.repositories.SeasonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CareerService {

    private static final Logger logger = LogManager.getLogger(PlayerService.class);


    @Autowired
    private CareerRepository repository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private FIFAService fifaService;

    @Autowired
    private StatisticService statisticService;



    public List<Career> getAllCareers() {
        return repository.findAll();
    }

    public Career getCareerById(String id) {
        Optional<Career> career = repository.findById(id);
        return career.orElse(null);
    }
    public Career createCareer(Career career) {
        if (career == null) {

            throw new IllegalArgumentException("Career cannot be null");
        }

        List<Season> seasons = new ArrayList<>();

        String season = fifaService.getInitialSeasonByFIFAVersion(career.getFifaCareer());
        Season initialSeason = new Season();
        initialSeason.setSeasonName(season);

        seasons.add(initialSeason);

        career.setSeasons(seasons);
        return repository.save(career);
    }

    public Career addPlayerToCareer(String careerId, Player player, String typeSeason) {
        Optional<Career> careerOpt = repository.findById(careerId);

        if (careerOpt.isPresent()) {
            Career career = careerOpt.get();


            boolean playerAddedToSpecificSeason = false;

            // Adicionamos o jogador à temporada específica, se aplicável
            for (Season season : career.getSeasons()) {
                if (season.getSeasonName().equals(typeSeason)) {
                    List<Player> players = season.getPlayers();
                    if (players == null) {
                        players = new ArrayList<>();
                    }

                    if (player.getStatisticsBySeasons() == null) {
                        List<Statistics> statistics = new ArrayList<Statistics>();
                        player.setStatisticsBySeasons(statistics);
                    }

                    for (Statistics statistics : player.getStatisticsBySeasons()) {
                        statistics.setSeason(typeSeason);
                    }
                    players.add(player);

                    StatisticsHistory history = player.getStatisticsHistory();

                    if (history == null) {
                        history = statisticService.convertStatisticsToStatisticsHistory(player.getStatisticsBySeasons().getFirst());
                    }

                    player.setStatisticsHistory(history);

                    season.setPlayers(players);
                    career.setPlayers(players);
                    playerAddedToSpecificSeason = true;
                    break;
                }
            }

            // Se a temporada específica não for encontrada, lançamos uma exceção
            if (!playerAddedToSpecificSeason && !typeSeason.equals("geral")) {
                throw new IllegalArgumentException("Temporada não encontrada");
            }

            Player savedPlayer = playerRepository.save(player);

            // Salvamos as alterações na carreira
            return repository.save(career);
        } else {
            throw new IllegalArgumentException("Career not found");
        }
    }

    public Career addSeasonToCareer(String careerId, Season season) {
        Optional<Career> careerOpt = repository.findById(careerId);

        if (careerOpt.isPresent()) {
            Career career = careerOpt.get();

            for (Season sea : career.getSeasons()) {
                if (Objects.equals(sea.getSeasonName(), season.getSeasonName())) {
                    throw new SeasonAlreadyExistsException("Ja existe essa temporada na carreira selecionada");
                }
            }

        Season seasonSaved = seasonService.createSeason(season);
        List<Season> seasons = career.getSeasons();
        seasons.add(seasonSaved);

        return repository.save(career);

        } else {
            throw new IllegalArgumentException("Career not found");
        }
    }

    public void deleteCareer(String id) {
        Career career = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "No technician found with this ID"));
        repository.delete(career);
    }

    public List<Player> getPlayersOfCareer(String careerId) {
       Career career = getCareerById(careerId);
        return new ArrayList<>(career.getPlayers());
    }
}
