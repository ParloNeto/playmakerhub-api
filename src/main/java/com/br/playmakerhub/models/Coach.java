package com.br.playmakerhub.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "coaches")
public class Coach {

    @Id
    private String id;
    private String coachesName;
    private String nationality;
    private String urlImageCoach;
    private Integer seasons;
}
