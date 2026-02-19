package com.calorietracker.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionResponse {

    private String apiPath;
    private int status;
    private String error;
    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public ExceptionResponse(
            String apiPath,
            HttpStatus status,
            String message
    ) {
        this.apiPath = apiPath;
        this.status = status.value();      // 400, 401, 403, etc.
        this.error = status.name();        // BAD_REQUEST, UNAUTHORIZED
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
