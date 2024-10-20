package com.br.playmakerhub.exceptions.season;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PreviousSeasonException extends RuntimeException {

    public PreviousSeasonException() {}

    public PreviousSeasonException(String ex) { super(ex); }
}
