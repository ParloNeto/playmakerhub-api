package com.br.playmakerhub.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class CoachDTO {

    private String coachesName;
    private String nationality;
    private String urlImageCoach;
}
