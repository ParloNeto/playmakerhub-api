package com.br.playmakerhub.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "player")
public class Player {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String nationality;

    private String position;

    private String kitNumber;

    private String joined;

    private String urlImagePlayer;

    private Statistics statistics;
}
