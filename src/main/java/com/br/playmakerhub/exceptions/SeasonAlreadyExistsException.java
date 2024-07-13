package com.br.playmakerhub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SeasonAlreadyExistsException extends RuntimeException {

    public SeasonAlreadyExistsException() {}

    public SeasonAlreadyExistsException(String ex) { super(ex); }
}
