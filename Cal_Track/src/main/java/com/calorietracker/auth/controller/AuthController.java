package com.calorietracker.auth.controller;

import com.calorietracker.auth.dto.LoginRequest;
import com.calorietracker.auth.dto.LoginResponseDto;
import com.calorietracker.auth.dto.RegisterRequest;
import com.calorietracker.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request){
               userService.register(request);
               return  ResponseEntity
                       .status(HttpStatus.CREATED)
                       .body("User registered successfully");

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequest loginRequest){
       String token= userService.login(loginRequest);
       return ResponseEntity.ok(new LoginResponseDto( token));
    }

    @GetMapping("/change")
    public String change(){
       String s=userService.change();
       return s;
    }

}
