package com.br.playmakerhub.mapper;

import com.br.playmakerhub.dto.SeasonDTO;
import com.br.playmakerhub.models.Season;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SeasonMapper {

    SeasonMapper INSTANCE = Mappers.getMapper(SeasonMapper.class);

    SeasonDTO seasonToSeasonDTO(Season season);

    Season seasonDtoToSeason(SeasonDTO seasonDTO);

}
