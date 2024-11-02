package com.br.playmakerhub.mapper;

import com.br.playmakerhub.dto.SeasonDTO;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Season;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SeasonMapper {

    SeasonMapper INSTANCE = Mappers.getMapper(SeasonMapper.class);

    SeasonDTO seasonToSeasonDTO(Season season);

    @Mapping(target = "games", defaultValue = "0")
    @Mapping(target = "wins", defaultValue = "0")
    @Mapping(target = "draws", defaultValue = "0")
    @Mapping(target = "losses", defaultValue = "0")
    @Mapping(target = "goalsConceded", defaultValue = "0")
    @Mapping(target = "goalsScored", defaultValue = "0")
    @Mapping(target = "players", expression = "java(defaultPlayers())")
    Season seasonDtoToSeason(SeasonDTO seasonDTO);

    default List<Player> defaultPlayers() { return new ArrayList<>(); }
}
