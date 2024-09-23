package com.br.playmakerhub.controller.exception;

import com.br.playmakerhub.exceptions.*;
import com.br.playmakerhub.exceptions.coach.CoachNotFoundException;
import com.br.playmakerhub.exceptions.league.LeagueNotFoundException;
import com.br.playmakerhub.exceptions.player.PlayerNotFoundException;
import com.br.playmakerhub.exceptions.season.SeasonAlreadyExistsException;
import com.br.playmakerhub.exceptions.season.SeasonNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<StandardError> playerNotFound(PlayerNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CoachNotFoundException.class)
    public ResponseEntity<StandardError> coachNotFound(CoachNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MissingFieldException.class)
    public ResponseEntity<StandardError> missingField(MissingFieldException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status.value(), e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }


    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status.value(), e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(SeasonNotFoundException.class)
    public ResponseEntity<StandardError> seasonNotFound(SeasonNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status.value(), e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(SeasonAlreadyExistsException.class)
    public ResponseEntity<StandardError> seasonAlreadyExists(SeasonAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(status.value(), e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
