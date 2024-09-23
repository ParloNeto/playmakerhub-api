package com.br.playmakerhub.exceptions.season;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)

public class SeasonNotFoundException extends RuntimeException {
    public SeasonNotFoundException(String msg){
        super(msg);
    }

    public SeasonNotFoundException(){
        super("Season not found");
    }
}
