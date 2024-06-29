package com.br.playmakerhub.models;

import lombok.Data;

@Data
public class Statistics {
    private String season;
    private Integer matchs;
    private Integer goals;
    private Integer assists;
    private Integer yellowCards;
    private Integer redCards;
    private Boolean contractedAtualSeason;
}
