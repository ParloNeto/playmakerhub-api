package com.br.playmakerhub.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "seasons")
public class Season {
    @Id
    private String id;
    private String seasonName;
    private Integer games;
    private Integer wins;
    private Integer draws;
    private Integer losses;
    private Integer goalsConceded;
    private Integer goalsScored;
    private List<Title> titles;
    private List<Player> players;
}
