package com.br.playmakerhub.services;

import com.br.playmakerhub.dto.PlayerDTO;
import com.br.playmakerhub.exceptions.MissingFieldException;
import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.exceptions.player.PlayerNotFoundException;
import com.br.playmakerhub.mapper.PlayerMapper;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Statistics;
import com.br.playmakerhub.models.StatisticsHistory;
import com.br.playmakerhub.repositories.PlayerRepository;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlayerService {

    private static final Logger logger = LogManager.getLogger(PlayerService.class);
    private static final String UPLOAD_DIR = "uploads/";


    @Autowired
    private PlayerRepository repository;

    public List<Player> getAllPlayers() {
        logger.info("getPlayerById() is called");
        return repository.findAll();
    }

    public Player getPlayerById(String id) {
        logger.info("getPlayerById() is called");
        Player entity = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("No player found with this ID"));
        logger.info("getPlayerById() is finished");
        return entity;
    }

    public Player createPlayer(PlayerDTO playerDto) {
        logger.info("createPlayer() is called");
        validatePlayer(playerDto);

        Player player = PlayerMapper.INSTANCE.playerDtoToPlayer(playerDto);
        player.setStatisticsHistory(createStatisticsHistory(player.getStatisticsBySeasons()));

        logger.info("createPlayer() is finished");
        return repository.save(player);
    }

    public Player updatePlayer(Player player) {
        logger.info("updatePlayer() is called");

        if (player == null) throw new PlayerNotFoundException("Player is null");

        Player entity = repository.findById(player.getId()).orElseThrow(() -> new ObjectNotFoundException(
                "No player found with this ID"));

        entity.setFirstName(player.getFirstName());
        entity.setLastName(player.getLastName());
        entity.setJoined(player.getJoined());
        entity.setUrlImagePlayer(player.getUrlImagePlayer());
        entity.setNationality(player.getNationality());
        entity.setPosition(player.getPosition());
        entity.setKitNumber(player.getKitNumber());
        entity.setStatisticsBySeasons(player.getStatisticsBySeasons());
        entity.setStatisticsHistory(createStatisticsHistory(player.getStatisticsBySeasons()));

        logger.info("updatePlayer() is finished");

        return repository.save(entity);
    }

    public void deletePlayer(String id) {
        logger.info("deletePlayer() is called");
        Player player = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "No player found with this ID"));
        logger.info("deletePlayer() is finished");
        repository.delete(player);
    }

    private static final Map<String, String> FIELD_LABELS = new HashMap<String, String>() {{
        put("firstName", "Nome");
        put("lastName", "Sobrenome");
        put("kitNumber", "Número da Camisa");
        put("nationality", "Nacionalidade");
        put("position", "Posição");
        put("joined", "Data de Ingresso");
        put("urlImagePlayer", "Imagem do Jogador");
    }};

    private void validatePlayer(PlayerDTO player) {
        try {
            if (player == null) {
                logger.error(PlayerNotFoundException.class);
                throw new PlayerNotFoundException("Player cannot be null");
            }

            for (Field field : PlayerDTO.class.getDeclaredFields()) {
                field.setAccessible(true);

                String fieldName = field.getName();
                String fieldLabel = FIELD_LABELS.getOrDefault(fieldName, fieldName); // Obtém o rótulo ou o nome do campo original

                if (field.get(player) == null) {
                    logger.error("Field '" + fieldLabel + "' is empty");
                    throw new MissingFieldException("Campo '" + fieldLabel + "' está faltando.");
                }

                if (field.get(player) instanceof String) {
                    String value = (String) field.get(player);
                    if (StringUtils.isBlank(value)) {
                        logger.error("Field '" + fieldLabel + "' is empty");
                        throw new MissingFieldException("Campo '" + fieldLabel + "' está vazio.");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }

    private StatisticsHistory createStatisticsHistory(List<Statistics> statisticsBySeasons) {
        if (statisticsBySeasons == null || statisticsBySeasons.isEmpty()) {
            throw new ObjectNotFoundException("List of Statistics is null or empty");
        }

        StatisticsHistory statisticsHistory = new StatisticsHistory();

        int totalMatches = 0;
        int totalGoals = 0;
        int totalAssists = 0;
        int totalYellowCards = 0;
        int totalRedCards = 0;

        for (Statistics statistics : statisticsBySeasons) {
            totalMatches += statistics.getMatches();
            totalGoals += statistics.getGoals();
            totalAssists += statistics.getAssists();
            totalYellowCards += statistics.getYellowCards();
            totalRedCards += statistics.getRedCards();
        }

        statisticsHistory.setMatches(totalMatches);
        statisticsHistory.setGoals(totalGoals);
        statisticsHistory.setAssists(totalAssists);
        statisticsHistory.setYellowCards(totalYellowCards);
        statisticsHistory.setRedCards(totalRedCards);

        return statisticsHistory;
    }

}
