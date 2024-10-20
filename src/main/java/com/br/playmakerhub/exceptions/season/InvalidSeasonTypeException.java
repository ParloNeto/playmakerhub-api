package com.br.playmakerhub.exceptions.season;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidSeasonTypeException extends RuntimeException {

    public InvalidSeasonTypeException() {}

    public InvalidSeasonTypeException(String season) {
        super("The season type '" + season + "' does not exist. Please enter a valid season.");
    }
}
