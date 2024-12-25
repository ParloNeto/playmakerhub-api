package com.br.playmakerhub.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@EqualsAndHashCode
@Document(collection = "players")
public class Player {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private Integer kitNumber;

    private String nationality;

    private String position;

    private String joined;

    private String urlImagePlayer;

    private List<Statistics> statisticsBySeasons;

    private StatisticsHistory statisticsHistory;

    private String idCareer;
}
