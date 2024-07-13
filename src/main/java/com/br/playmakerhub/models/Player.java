package com.br.playmakerhub.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "players")
public class Player {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String kitNumber;

    private String nationality;

    private String position;

    private String joined;

    private String urlImagePlayer;

    private List<Statistics> statisticsBySeasons;

    private StatisticsHistory statisticsHistory;
}
