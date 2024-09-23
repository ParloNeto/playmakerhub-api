package com.br.playmakerhub.models;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class League {

    @Id
    private String id;
    private String name;
    private List<Team> teams;
}
