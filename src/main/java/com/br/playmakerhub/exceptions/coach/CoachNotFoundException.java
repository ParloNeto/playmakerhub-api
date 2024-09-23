package com.br.playmakerhub.exceptions.coach;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CoachNotFoundException extends RuntimeException {

    public CoachNotFoundException() {}

    public CoachNotFoundException(String ex) { super(ex); }
}
