package com.br.playmakerhub.mapper;

import com.br.playmakerhub.dto.CareerDTO;
import com.br.playmakerhub.models.Career;
import com.br.playmakerhub.services.CareerService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CareerMapper {

    CareerMapper INSTANCE = Mappers.getMapper(CareerMapper.class);

    CareerDTO careerToCareerDTO(Career career);

    Career careerDtoToCareer(CareerDTO careerDTO);
}
