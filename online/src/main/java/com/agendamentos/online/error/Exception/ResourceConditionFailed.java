package com.agendamentos.online.error.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
public class ResourceConditionFailed extends RuntimeException{
    
    public ResourceConditionFailed(String msg){
        super(msg);
    }
    
}
