package com.br.playmakerhub.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Title {

    @Id
    private String id;
    private String titleName;
}
