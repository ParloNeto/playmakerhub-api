package com.br.playmakerhub.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document(collection = "careers")
public class Career {

    @Id
    private String id;
    private Coach coach;
    private String fifaCareer;
    private String leagueCareer;
    private String teamCareer;
    private List<Season> seasons;
    private List<Player> players;
    private CareerHistory careerHistory;
}
