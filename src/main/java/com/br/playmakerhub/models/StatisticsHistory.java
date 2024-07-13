package com.br.playmakerhub.models;

import lombok.Data;

@Data
public class StatisticsHistory {
    private Integer matches;
    private Integer goals;
    private Integer assists;
    private Integer yellowCards;
    private Integer redCards;
}
