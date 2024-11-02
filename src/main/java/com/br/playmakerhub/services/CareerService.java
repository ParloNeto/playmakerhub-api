package com.br.playmakerhub.services;

import com.br.playmakerhub.dto.CareerDTO;
import com.br.playmakerhub.dto.PlayerDTO;
import com.br.playmakerhub.dto.PlayerStatsDTO;
import com.br.playmakerhub.dto.SeasonDTO;
import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.exceptions.career.CareerNotFoundException;
import com.br.playmakerhub.exceptions.player.PlayerNotFoundException;
import com.br.playmakerhub.exceptions.season.InvalidSeasonTypeException;
import com.br.playmakerhub.exceptions.season.PreviousSeasonException;
import com.br.playmakerhub.exceptions.season.SeasonAlreadyExistsException;
import com.br.playmakerhub.exceptions.season.SeasonNotFoundException;
import com.br.playmakerhub.mapper.CareerMapper;
import com.br.playmakerhub.mapper.PlayerMapper;
import com.br.playmakerhub.models.*;
import com.br.playmakerhub.models.enums.TypeSeason;
import com.br.playmakerhub.repositories.CareerRepository;
import com.br.playmakerhub.repositories.PlayerRepository;
import com.br.playmakerhub.repositories.SeasonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CareerService {

    private static final Logger logger = LogManager.getLogger(PlayerService.class);


    @Autowired
    private CareerRepository repository;

    @Autowired
    private SeasonRepository seasonRepository;

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
        return career.orElseThrow(CareerNotFoundException::new);
    }

    public Page<PlayerStatsDTO> getStatisticsPlayersCareer(String id, Pageable pageable, Comparator<? super PlayerStatsDTO> comparator) {
        Career career = getCareerById(id);

        if (career.getPlayers() == null) {
            throw new PlayerNotFoundException("Não foi possível encontrar jogadores associados a essa carreira");
        }

        List<PlayerStatsDTO> playerStatsDTOList = PlayerMapper.INSTANCE.convertListPlayerToListStatsDTO(career.getPlayers());

        List<PlayerStatsDTO> sortedList = playerStatsDTOList.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        return getPageFromList(sortedList, pageable);
    }

    public Page<PlayerStatsDTO> getStatisticsPlayersCareerByGoals(String id, Pageable pageable) {
        return getStatisticsPlayersCareer(id, pageable, Comparator.comparing(PlayerStatsDTO::getGoals).reversed());
    }

    public Page<PlayerStatsDTO> getStatisticsPlayersCareerByAssists(String id, Pageable pageable) {
        return getStatisticsPlayersCareer(id, pageable, Comparator.comparing(PlayerStatsDTO::getAssists).reversed());
    }




    public Career createCareer(CareerDTO careerDTO) {
        if (careerDTO == null) {
            throw new IllegalArgumentException("Career cannot be null");
        }

        Coach coach = coachService.createCoach(careerDTO.getCoach());

        Career career = CareerMapper.INSTANCE.careerDtoToCareer(careerDTO);
        career.setCareerHistory(new CareerHistory(0,0,0,0,0,0,null));

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
        Career career = getCareerById(careerId);

        playerDTO.setIdCareer(careerId);
        Player savedPlayer = playerService.createPlayer(playerDTO);

        if ("geral".equalsIgnoreCase(typeSeason)) {
            addPlayerToGeneralCareer(career, savedPlayer);
        } else if (isValidSeason(typeSeason)) {
            addPlayerToSpecificSeason(career, savedPlayer, typeSeason);
        } else {
            throw new InvalidSeasonTypeException(typeSeason);
        }

        return repository.save(career);
    }



    public List<Player> getAvailablePlayersForSeason(String careerId, String seasonName) {

        Career career = getCareerById(careerId);

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

        Career career = getCareerById(careerId);

        Player player = playerRepository.findById(playerId).orElseThrow(PlayerNotFoundException::new);

        Season seasonFiltered = career.getSeasons().stream()
                .filter(season::equals)
                .findFirst()
                .orElseThrow(() -> new SeasonNotFoundException("Temporada nao encontrada."));

        if (seasonFiltered.getPlayers() == null) {
            seasonFiltered.setPlayers(new ArrayList<>());
        }

        seasonFiltered.getPlayers().add(player);
        seasonRepository.save(seasonFiltered);
        return repository.save(career);
    }

    public Career addSeasonToCareer(String careerId, SeasonDTO seasonDTO) {
        Career career = getCareerById(careerId);

        validateNewSeason(career, seasonDTO.getSeasonName());

        Season newSeason = seasonService.createSeason(seasonDTO);
        if (career.getPlayers() != null) {
            newSeason = seasonService.setPlayersToSeason(newSeason.getId(), career.getPlayers());
        }

        career.getSeasons().add(newSeason);

        CareerHistory updatedCareerHistory = updateCareerHistory(career.getSeasons(), career.getCareerHistory());
        career.setCareerHistory(updatedCareerHistory);

        return repository.save(career);
    }




    public void deleteCareer(String id) {
        Career career = repository.findById(id).orElseThrow(() -> new CareerNotFoundException(
                "Carreira não encontrada pelo ID"));
        repository.delete(career);
    }

    public List<Player> getPlayersOfCareer(String careerId) {
       Career career = getCareerById(careerId);
       if (career.getPlayers() == null)
           throw new PlayerNotFoundException("Não foi possível encontrar jogadores associados a essa temporada.");

       return new ArrayList<>(career.getPlayers());
    }

    public List<Player> getAllPlayersBySeasonAndCareer(String careerId, String typeSeason) {
        Career careerOpt = getCareerById(careerId);

        return careerOpt.getSeasons().stream()
                .filter(season -> typeSeason.equals(season.getSeasonName()))
                .flatMap(season -> {
                    if (season.getPlayers() == null)
                        throw new PlayerNotFoundException("Não foi possível encontrar jogadores associados a essa temporada.");

                    return season.getPlayers().stream();
                })
                .collect(Collectors.toList());
    }

        public List<Player> getPlayersBySeasonAndPosition(String careerId, String typeSeason, String positionPlayer) {
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
        Career careerOpt = getCareerById(careerId);
        return careerOpt.getSeasons();
    }

    public Season getSeasonByCareer(String careerId, String typeSeason) {
        Career career = getCareerById(careerId);
        return career.getSeasons().stream()
                .filter((seasonInCareer) -> seasonInCareer.getSeasonName().equalsIgnoreCase(typeSeason))
                .findFirst()
                    .orElseThrow(SeasonNotFoundException::new);
    }


    public CareerHistory updateCareerHistory(List<Season> seasons, CareerHistory careerHistory) {
        return statisticService.calculateCareerHistory(seasons, careerHistory);
    }

    public Career updateCareerHistoryByFirstSeason(String careerId) {
        Career career = getCareerById(careerId);
        CareerHistory careerHistory = career.getCareerHistory();

        List<Season> seasons = career.getSeasons().stream()
                .map(dbRefSeason -> seasonService.getSeasonById(dbRefSeason.getId()))
                .toList();

        CareerHistory careerHistorySum = updateCareerHistory(seasons, careerHistory);

        career.setCareerHistory(careerHistorySum);
        return repository.save(career);
    }



    private Page<PlayerStatsDTO> getPageFromList(List<PlayerStatsDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<PlayerStatsDTO> subList = list.subList(start, end);

        return new PageImpl<>(subList, pageable, list.size());
    }

    private void validateNewSeason(Career career, String seasonName) {
        String firstSeason = career.getSeasons().get(0).getSeasonName();
        if (isPreviousSeason(firstSeason, seasonName)) {
            throw new PreviousSeasonException("A temporada não pode ser anterior à primeira.");
        }
        career.getSeasons().stream()
                .filter(season -> season.getSeasonName().equals(seasonName))
                .findFirst()
                .ifPresent(season -> {
                    throw new SeasonAlreadyExistsException("Essa temporada já existe.");
                });
    }

    private boolean isPreviousSeason(String temporada1, String temporada2) {
        int anoInicialTemporada1 = Integer.parseInt(temporada1.split("-")[1]);
        int anoFinalTemporada1 = Integer.parseInt(temporada1.split("-")[2]);

        int anoInicialTemporada2 = Integer.parseInt(temporada2.split("-")[1]);
        int anoFinalTemporada2 = Integer.parseInt(temporada2.split("-")[2]);

        return anoInicialTemporada2 < anoInicialTemporada1 ||
                (anoInicialTemporada2 == anoInicialTemporada1 && anoFinalTemporada2 < anoFinalTemporada1);
    }

    private void addPlayerToGeneralCareer(Career career, Player savedPlayer) {
        if (career.getPlayers() == null) {
            career.setPlayers(new ArrayList<>());
        }
        career.getPlayers().add(savedPlayer);
    }

    private void addPlayerToSpecificSeason(Career career, Player savedPlayer, String typeSeason) {
        Season season = career.getSeasons().stream()
                .filter(s -> typeSeason.equals(s.getSeasonName()))
                .findFirst()
                .orElseThrow(() -> new InvalidSeasonTypeException(typeSeason));

        if (season.getPlayers() == null) {
            season.setPlayers(new ArrayList<>());
        }

        addPlayerToGeneralCareer(career, savedPlayer);
        season.getPlayers().add(savedPlayer);
        this.seasonRepository.save(season);
    }

    private boolean isValidSeason(String typeSeason) {
        return Arrays.stream(TypeSeason.values())
                .map(TypeSeason::getName)
                .anyMatch(value -> value.equalsIgnoreCase(typeSeason));
    }
}
