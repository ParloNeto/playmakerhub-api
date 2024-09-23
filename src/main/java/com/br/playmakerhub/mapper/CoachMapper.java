package com.br.playmakerhub.mapper;

import com.br.playmakerhub.dto.CoachDTO;
import com.br.playmakerhub.models.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoachMapper {

    CoachMapper INSTANCE = Mappers.getMapper(CoachMapper.class);

    CoachDTO coachToCoachDTO(Coach coach);

    Coach coachDtoToCoach(CoachDTO coachDTO);
}