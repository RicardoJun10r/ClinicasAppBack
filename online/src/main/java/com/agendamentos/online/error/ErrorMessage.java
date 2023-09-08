package com.agendamentos.online.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {
    
    private String titulo;
    
    private Integer status_code;
    
    private String mensagem;

    public ErrorMessage(String titulo, Integer status, String message) {
        this.titulo = titulo;
        this.status_code = status;
        this.mensagem = message;
    }

}
