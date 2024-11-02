package com.br.playmakerhub.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
public class CareerHistory {
    private Integer games;
    private Integer wins;
    private Integer draws;
    private Integer losses;
    private Integer goalsConceded;
    private Integer goalsScored;
    private List<Title> titles;
}
