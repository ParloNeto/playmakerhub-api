package com.br.playmakerhub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class CoachIsNullException extends RuntimeException {

    public CoachIsNullException() {}

    public CoachIsNullException(String ex) { super(ex); }
}
