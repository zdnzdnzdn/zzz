package com.example.testserverapp.exception;

import com.example.testserverapp.model.response.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessageResponse> customExceptionHandler(CustomException customException) {
        ErrorMessageResponse errorMessageResponse = ErrorMessageResponse.builder()
            .message(customException.getMessage())
            .code(customException.getCode())
            .build();
        
        return new ResponseEntity<>(errorMessageResponse, HttpStatus.valueOf(customException.getStatus()));
    }
}
