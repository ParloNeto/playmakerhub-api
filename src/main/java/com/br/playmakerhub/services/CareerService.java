package com.br.playmakerhub.services;

import com.br.playmakerhub.dto.CareerDTO;
import com.br.playmakerhub.dto.PlayerDTO;
import com.br.playmakerhub.dto.PlayerStatsDTO;
import com.br.playmakerhub.dto.SeasonDTO;
import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.exceptions.player.PlayerNotFoundException;
import com.br.playmakerhub.exceptions.season.InvalidSeasonTypeException;
import com.br.playmakerhub.exceptions.season.PreviousSeasonException;
import com.br.playmakerhub.exceptions.season.SeasonAlreadyExistsException;
import com.br.playmakerhub.exceptions.season.SeasonNotFoundException;
import com.br.playmakerhub.mapper.CareerMapper;
import com.br.playmakerhub.models.*;
import com.br.playmakerhub.models.enums.TypeSeason;
import com.br.playmakerhub.repositories.CareerRepository;
import com.br.playmakerhub.repositories.PlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    private PlayerService playerService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private FIFAService fifaService;

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private CoachService coachService;



    public List<Career> getAllCareers() {
        return repository.findAll();
    }

    public Career getCareerById(String id) {
        Optional<Career> career = repository.findById(id);
        return career.orElse(null);
    }

    public Page<PlayerStatsDTO> getStatisticsPlayersCareerByGoals(String id, Pageable pageable) {
        Career career = repository.findById(id).orElseThrow(() -> new SeasonNotFoundException("Carreira nao encontrada"));;

        if (career.getPlayers().isEmpty()) {
            return null;
        }

        List<PlayerStatsDTO> sortedList = statisticService.sortPlayerByGoals(career.getPlayers());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedList.size());
        List<PlayerStatsDTO> subList = sortedList.subList(start, end);

        return new PageImpl<>(subList, pageable, sortedList.size());
    }

    public Page<PlayerStatsDTO> getStatisticsPlayersCareerByAssists(String id, Pageable pageable) {
        Career career = repository.findById(id).orElseThrow(() -> new SeasonNotFoundException("Carreira nao encontrada"));;

        if (career.getPlayers().isEmpty()) {
            return null;
        }

        List<PlayerStatsDTO> sortedList = statisticService.sortPlayerByAssists(career.getPlayers());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sortedList.size());
        List<PlayerStatsDTO> subList = sortedList.subList(start, end);

        return new PageImpl<>(subList, pageable, sortedList.size());
    }
    public Career createCareer(CareerDTO careerDTO) {
        if (careerDTO == null) {
            throw new IllegalArgumentException("Career cannot be null");
        }

        Coach coach = coachService.createCoach(careerDTO.getCoach());

        Career career = CareerMapper.INSTANCE.careerDtoToCareer(careerDTO);

        String season = fifaService.getInitialSeasonByFIFAVersion(career.getFifaCareer());
        SeasonDTO initialSeasonDTO = new SeasonDTO();

        initialSeasonDTO.setSeasonName(season);

        Season initialSeason = seasonService.createSeason(initialSeasonDTO);

        List<Season> seasons = new ArrayList<>();
        seasons.add(initialSeason);

        career.setSeasons(seasons);
        career.setCoach(coach);
        return repository.save(career);
    }

    public Career addPlayerToCareer(String careerId, PlayerDTO playerDTO, String typeSeason) {
        Optional<Career> careerOpt = repository.findById(careerId);

        if (careerOpt.isPresent()) {
            Career career = careerOpt.get();
            Optional<List<Player>> playersCareer = Optional.ofNullable(career.getPlayers());

            boolean playerAddedToSpecificSeason = false;

            playerDTO.setIdCareer(careerOpt.get().getId());

            if (typeSeason.equals("geral")) {

                Player savedPlayer = playerService.createPlayer(playerDTO);

                if (playersCareer.isEmpty()) {
                    List<Player> players = new ArrayList<>();
                    career.setPlayers(players);
                }

                if (playersCareer.isPresent()) {
                    career.getPlayers().add(savedPlayer);
                }

            } else if (isValidSeason(typeSeason)) {
                for (Season season : career.getSeasons()) {

                    List<Player> players = new ArrayList<>();

                    if (season.getPlayers() != null) {
                        players = season.getPlayers();
                    }

                    if (playerDTO.getStatisticsBySeasons() == null) {
                        List<Statistics> statistics = new ArrayList<Statistics>();
                        playerDTO.setStatisticsBySeasons(statistics);
                    }


                    if (season.getSeasonName().equals(typeSeason)) {

                        for (Statistics statistics : playerDTO.getStatisticsBySeasons()) {
                            statistics.setSeason(typeSeason);
                        }

                        playerDTO.setIdCareer(careerOpt.get().getId());

                        Player savedPlayer = playerService.createPlayer(playerDTO);

                        players.add(savedPlayer);

                        StatisticsHistory history = savedPlayer.getStatisticsHistory();

                        if (history == null) {
                            history = statisticService.convertStatisticsToStatisticsHistory(savedPlayer.getStatisticsBySeasons().getFirst());
                        }

                        savedPlayer.setStatisticsHistory(history);

                        season.setPlayers(players);
                        career.setPlayers(players);
                        playerAddedToSpecificSeason = true;
                        break;
                    }
                }
            } else {
                throw new InvalidSeasonTypeException(typeSeason);
            }
            return repository.save(career);
        } else {
            throw new IllegalArgumentException("Career not found");
        }
    }

    public List<Player> getAvailablePlayersForSeason(String careerId, String seasonName) {

        Career career = repository.findById(careerId)
                .orElseThrow(() -> new SeasonNotFoundException("Carreira não encontrada."));

        List<Player> allPlayers = career.getPlayers();

        Season season = career.getSeasons().stream()
                .filter(seasonRes -> seasonName.equals(seasonRes.getSeasonName()))
                .findFirst()
                .orElseThrow(() -> new SeasonNotFoundException("Temporada não encontrada."));

        List<Player> playersInSeason = season.getPlayers();

        List<Player> availablePlayers = allPlayers.stream()
                .filter(player -> !playersInSeason.contains(player))
                .collect(Collectors.toList());

        return availablePlayers;
    }


    public Career updatePlayerToSeason(String careerId, String playerId, Season season) {

        Career career = repository.findById(careerId).orElseThrow(() -> new SeasonNotFoundException("Carreira nao encontrada."));

        Player player = playerRepository.findById(playerId).orElseThrow(PlayerNotFoundException::new);

        Season seasonFiltered = career.getSeasons().stream()
                .filter(season::equals)
                .findFirst()
                .orElseThrow(() -> new SeasonNotFoundException("Temporada nao encontrada."));

        if (seasonFiltered.getPlayers() == null) {
            seasonFiltered.setPlayers(new ArrayList<>());
        }

        seasonFiltered.getPlayers().add(player);

        return repository.save(career);

    }

    public Career addSeasonToCareer(String careerId, SeasonDTO seasonDTO) {
        Career career = repository.findById(careerId).orElseThrow(() -> new SeasonNotFoundException("Carreira nao encontrada."));
        String firstSeason = career.getSeasons().getFirst().getSeasonName();

        if (isPreviousSeason(firstSeason, seasonDTO.getSeasonName())) {
            throw new PreviousSeasonException("A temporada selecionada não pode ser anterior à primeira temporada criada na carreira.");
        }

            for (Season sea : career.getSeasons()) {
                if (Objects.equals(sea.getSeasonName(), seasonDTO.getSeasonName())) {
                    throw new SeasonAlreadyExistsException("Ja existe essa temporada na carreira selecionada.");
                }

            }

        Season seasonSaved = seasonService.createSeason(seasonDTO);

            if (career.getPlayers() != null) {
                seasonSaved = seasonService.setPlayersToSeason(seasonSaved.getId(), career.getPlayers());
            }

        List<Season> seasons = career.getSeasons();
        seasons.add(seasonSaved);

        return repository.save(career);
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

    public List<Player> getAllPlayersBySeasonAndCareer(String careerId, String typeSeason) {
        Career careerOpt = repository.findById(careerId).orElseThrow(() -> new SeasonNotFoundException("Carreira nao encontrada"));

    return careerOpt.getSeasons().stream()
            .filter(season -> typeSeason.equals(season.getSeasonName()))
            .flatMap(season -> season.getPlayers().stream())
            .collect(Collectors.toList());
    }

    public List<Player> getAllPlayersBySeasonAndCareerByPosition(String careerId, String typeSeason, String positionPlayer) {
        List<Player> playerList = getAllPlayersBySeasonAndCareer(careerId, typeSeason);
        List<Player> filteredPlayers = playerList.stream()
                .filter(res -> positionPlayer.equals(res.getPosition()))
                .collect(Collectors.toList());
        if (filteredPlayers.isEmpty()) {
            throw new PlayerNotFoundException();
        }
        return filteredPlayers;
    }

    public List<Season> getSeasonsByCareer(String careerId) {
        Career careerOpt = repository.findById(careerId).orElseThrow(() -> new SeasonNotFoundException("Carreira nao encontrada"));

        return careerOpt.getSeasons();
    }

    private boolean isPreviousSeason(String temporada1, String temporada2) {
        int anoInicialTemporada1 = Integer.parseInt(temporada1.split("-")[1]);
        int anoFinalTemporada1 = Integer.parseInt(temporada1.split("-")[2]);

        int anoInicialTemporada2 = Integer.parseInt(temporada2.split("-")[1]);
        int anoFinalTemporada2 = Integer.parseInt(temporada2.split("-")[2]);

        return anoInicialTemporada2 < anoInicialTemporada1 ||
                (anoInicialTemporada2 == anoInicialTemporada1 && anoFinalTemporada2 < anoFinalTemporada1);
    }

    public boolean isValidSeason(String typeSeason) {
        return Arrays.stream(TypeSeason.values())
                .map(TypeSeason::getName)
                .anyMatch(value -> value.equalsIgnoreCase(typeSeason));
    }
}
