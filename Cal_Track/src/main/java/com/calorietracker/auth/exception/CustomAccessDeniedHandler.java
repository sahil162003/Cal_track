package com.calorietracker.auth.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.calorietracker.auth.dto.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) {
        try {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");

            ExceptionResponse error = new ExceptionResponse(
                    request.getRequestURI(),
                    HttpStatus.FORBIDDEN,
                    "Access is denied"
            );

            objectMapper.writeValue(response.getWriter(), error);
        } catch (Exception e) {
            // last-resort fallback
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
}
