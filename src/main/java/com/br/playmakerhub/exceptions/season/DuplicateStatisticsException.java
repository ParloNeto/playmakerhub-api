package com.br.playmakerhub.exceptions.season;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class DuplicateStatisticsException extends RuntimeException {
    public DuplicateStatisticsException(String msg){
        super(msg);
    }

    public DuplicateStatisticsException(){
        super("Já existe estatisticas criadas para esse jogador nessa temporada.");
    }
}
