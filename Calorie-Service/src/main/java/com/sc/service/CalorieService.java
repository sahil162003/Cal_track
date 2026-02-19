package com.sc.service;


import com.sc.dto.CalorieRequest;
import com.sc.dto.CalorieResponse;
import com.sc.entity.CalorieEntry;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CalorieService {
    CalorieResponse add(String name, CalorieRequest calorieRequest);

    List<CalorieResponse> getToday(String name);

    List<CalorieResponse> getAll(String name);

    int getTodayTotal(String name);

    void delete(Long id, String name);

    CalorieResponse update(Long id, String name, CalorieRequest request);
}
