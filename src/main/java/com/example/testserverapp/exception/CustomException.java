package com.example.testserverapp.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {
  
    private final String code;
    private final int status;

    public CustomException(String message, String code, int status) {
        super(message); 
        this.code = code; 
        this.status = status; 
    }
}
