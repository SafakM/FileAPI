package com.safak.filemanagement.Exception;

import com.safak.filemanagement.Exception.CustomExceptions.BadRequestException;
import com.safak.filemanagement.Exception.CustomExceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleVehicleNotFoundException(NotFoundException ex, WebRequest request){
        ApiError apiError = new ApiError(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request){
        ApiError apiError = new ApiError(LocalDateTime.now(),ex.getMessage(),request.getDescription(false));

        return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
    }
}
