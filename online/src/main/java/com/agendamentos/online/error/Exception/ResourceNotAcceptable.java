package com.agendamentos.online.error.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class ResourceNotAcceptable extends RuntimeException {
    
    public ResourceNotAcceptable(String msg){
        super(msg);
    }

}
