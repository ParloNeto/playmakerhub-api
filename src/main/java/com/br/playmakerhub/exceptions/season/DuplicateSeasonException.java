package com.br.playmakerhub.exceptions.season;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class DuplicateSeasonException extends RuntimeException {
    public DuplicateSeasonException(String msg){
        super(msg);
    }

    public DuplicateSeasonException(){
        super("Já está cadastrada essa temporada na carreira");
    }
}
