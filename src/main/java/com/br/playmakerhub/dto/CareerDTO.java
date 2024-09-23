package com.br.playmakerhub.dto;

import com.br.playmakerhub.models.CareerHistory;
import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Season;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
public class CareerDTO {

    private CoachDTO coach;
    private String fifaCareer;
    private String leagueCareer;
    private String teamCareer;
}
