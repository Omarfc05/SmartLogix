package com.storechain.shipment.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

//Esto no era para aplicar reglas de negocio en el sistema?
@Data
public class BusinessRuleException extends Exception {

    public BusinessRuleException(long id, String code, HttpStatus httpStatus, String message) {
        super(message);
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }
    
    public BusinessRuleException(String code, HttpStatus httpStatus, String message) {
        super(message);
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }
    
     public BusinessRuleException(HttpStatus httpStatus, String message) {
        super(message);
        this.id = id;
        this.code = code;
        this.httpStatus = httpStatus;
    }


    private long id;
    private String code;
    private HttpStatus httpStatus;

}
