package com.br.playmakerhub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class PlayerIsNullException extends RuntimeException {

    public PlayerIsNullException() {}

    public PlayerIsNullException(String ex) { super(ex); }
}
