package com.br.playmakerhub.models.enums;

public enum TypeSeason {

    TEMPORADA_2014("temporada-14-15"),
    TEMPORADA_2015("temporada-15-16"),
    TEMPORADA_2016("temporada-16-17"),
    TEMPORADA_2017("temporada-17-18"),
    TEMPORADA_2018("temporada-18-19"),
    TEMPORADA_2019("temporada-19-20"),
    TEMPORADA_2020("temporada-20-21"),
    TEMPORADA_2021("temporada-21-22"),
    TEMPORADA_2022("temporada-22-23"),
    TEMPORADA_2023("temporada-23-24"),
    TEMPORADA_2024("temporada-24-25"),
    TEMPORADA_2025("temporada-25-26"),
    TEMPORADA_2026("temporada-26-27");

    private String name;

    TypeSeason(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
