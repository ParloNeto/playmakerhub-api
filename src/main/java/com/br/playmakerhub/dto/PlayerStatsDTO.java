package com.br.playmakerhub.dto;

import lombok.Data;

@Data
public class PlayerStatsDTO {

    private String id;
    private String firstName;
    private String lastName;
    private Integer goals;
    private Integer assists;


}
