package com.br.playmakerhub.mapper;

import com.br.playmakerhub.dto.PlayerDTO;
import com.br.playmakerhub.dto.PlayerStatsDTO;
import com.br.playmakerhub.models.Player;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);


    PlayerDTO playerToPlayerDTO(Player player);

    Player playerDtoToPlayer(PlayerDTO playerDTO);

    @Mapping(target = "goals", source = "statisticsHistory.goals")
    @Mapping(target = "assists", source = "statisticsHistory.assists")
    PlayerStatsDTO playerToPlayerStatsDTO(Player player);


    List<PlayerDTO> convertListPlayerToListDTO(Iterable<Player> players);


    List<PlayerStatsDTO> convertListPlayerToListStatsDTO(Iterable<Player> players);

    @AfterMapping
    default void handleNullList(@MappingTarget List<PlayerDTO> listPlayer) {
        if (listPlayer == null) {
            listPlayer = new ArrayList<>();
        }
    }

    List<Player> convertListDtoToListPlayer(Iterable<PlayerDTO> playersDto);


}
