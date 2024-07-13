package com.br.playmakerhub.services;

import com.br.playmakerhub.exceptions.MissingFieldException;
import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.exceptions.PlayerIsNullException;
import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.repositories.PlayerRepository;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class PlayerService {

    private static final Logger logger = LogManager.getLogger(PlayerService.class);

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

    public Player createPlayer(Player player) throws IllegalAccessException {
        logger.info("createPlayer() is called");
        validatePlayer(player);
        logger.info("createPlayer() is finished");
        return repository.save(player);
    }

    public void deletePlayer(String id) {
        logger.info("deletePlayer() is called");
        Player player = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "No player found with this ID"));
        logger.info("deletePlayer() is finished");
        repository.delete(player);
    }

    private void validatePlayer(Player player) throws IllegalAccessException {
        if (player == null) {
            logger.error(PlayerIsNullException.class);
            throw new PlayerIsNullException("Player cannot be null");
        }

        for (Field field : Player.class.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals("id")) continue;
            if (field.getName().equals("statistics")) continue;
            if (field.get(player) == null) {
                logger.error("Field '" + field.getName() + "' is empty");

                throw new MissingFieldException("Campo '" + field.getName() + "' está faltando.");
            }
            if (field.get(player) instanceof String) {
                String value = (String) field.get(player);
                if (StringUtils.isBlank(value)) {
                    logger.error("Field '" + field.getName() + "' is empty");
                    throw new MissingFieldException("Campo '" + field.getName() + "' está vazio.");
                }
            }
        }
    }

}
