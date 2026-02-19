package com.sc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<String> handletest(Authentication authentication){
        String s=authentication.getName();
        return ResponseEntity.status(HttpStatus.OK)
                .body("Helo"+s);
    }

    @GetMapping("/public/hello")
    public String publicApi() {
        return "Public endpoint works";
    }
}
