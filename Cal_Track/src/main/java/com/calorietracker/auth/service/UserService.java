package com.calorietracker.auth.service;

import com.calorietracker.auth.dto.LoginRequest;
import com.calorietracker.auth.dto.RegisterRequest;
import jakarta.validation.Valid;

public interface UserService  {

    void register(@Valid RegisterRequest request);

    String login(@Valid LoginRequest loginRequest);

    String change();
}
