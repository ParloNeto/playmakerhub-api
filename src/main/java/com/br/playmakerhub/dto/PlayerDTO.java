package com.br.playmakerhub.dto;

import com.br.playmakerhub.models.Statistics;
import com.br.playmakerhub.models.StatisticsHistory;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class PlayerDTO {

    private String firstName;

    private String lastName;

    private Integer kitNumber;

    private String nationality;

    private String position;

    private String joined;

    private String urlImagePlayer;

    private String idCareer;

    private List<Statistics> statisticsBySeasons;

}
