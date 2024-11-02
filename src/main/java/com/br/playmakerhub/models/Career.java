package com.br.playmakerhub.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document(collection = "careers")
public class Career {

    @Id
    private String id;

    @DBRef
    private Coach coach;
    private String fifaCareer;
    private String leagueCareer;
    private String teamCareer;
    @DBRef
    private List<Season> seasons;
    @DBRef
    private List<Player> players;
    private CareerHistory careerHistory;
}
