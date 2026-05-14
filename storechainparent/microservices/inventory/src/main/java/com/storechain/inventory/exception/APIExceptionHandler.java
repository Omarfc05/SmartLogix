package com.storechain.inventory.exception;

import com.storechain.inventory.common.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class APIExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknowHostException(Exception ex)
    {
        ExceptionResponse respuesta = new ExceptionResponse("Técnico"
                , "Input ouput error"
                , "4545"
                , ex.getMessage()
                , "");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(respuesta);
    }
    
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<?> handleBusinessRuleException(BusinessRuleException ex)
    {
        ExceptionResponse respuesta = new ExceptionResponse("Business"
                , "Error de validación o de negocio"
                , ex.getCode()
                , ex.getMessage()
                , "");
        return ResponseEntity.status(ex.getHttpStatus()).body(respuesta);
    }
    
}
