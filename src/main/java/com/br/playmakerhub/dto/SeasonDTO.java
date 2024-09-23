package com.br.playmakerhub.dto;

import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Title;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class SeasonDTO {

    private String seasonName;
    private Integer games;
    private Integer wins;
    private Integer draws;
    private Integer losses;
    private Integer goalsConceded;
    private Integer goalsScored;
}
