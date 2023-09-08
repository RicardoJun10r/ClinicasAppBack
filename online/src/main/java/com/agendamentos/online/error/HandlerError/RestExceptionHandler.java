package com.agendamentos.online.error.HandlerError;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.agendamentos.online.error.ErrorMessage;
import com.agendamentos.online.error.Exception.ResourceBadRequest;
import com.agendamentos.online.error.Exception.ResourceConditionFailed;
import com.agendamentos.online.error.Exception.ResourceForbidden;
import com.agendamentos.online.error.Exception.ResourceNotAcceptable;
import com.agendamentos.online.error.Exception.ResourceNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFoundError(ResourceNotFoundException resourceNotFoundException){
        ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, resourceNotFoundException.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConditionFailed.class)
    public ResponseEntity<?> handlePreconditionFailed(ResourceConditionFailed resourceConditionFailed){
        ErrorMessage errorMessage = new ErrorMessage("Precondition Failed", 412, resourceConditionFailed.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(ResourceForbidden.class)
    public ResponseEntity<?> handlePreconditionFailed(ResourceForbidden resourceForbiden){
        ErrorMessage errorMessage = new ErrorMessage("Forbidden", 403, resourceForbiden.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceBadRequest.class)
    public ResponseEntity<?> handleResourceBadRequest(ResourceBadRequest resourceBadRequest){
        ErrorMessage errorMessage = new ErrorMessage("Bad Request", 400, resourceBadRequest.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotAcceptable.class)
    public ResponseEntity<?> handleResourceNotAcceptable(ResourceNotAcceptable resourceBadRequest){
        ErrorMessage errorMessage = new ErrorMessage("Not Acceptable", 406, resourceBadRequest.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
    }
    
}
