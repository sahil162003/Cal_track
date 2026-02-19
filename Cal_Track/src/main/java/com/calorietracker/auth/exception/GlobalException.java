package com.calorietracker.auth.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<com.calorietracker.auth.dto.ExceptionResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new com.calorietracker.auth.dto.ExceptionResponse(
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage()
                ));
    }




}
