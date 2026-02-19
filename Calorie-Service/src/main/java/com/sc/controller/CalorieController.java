package com.sc.controller;

import com.sc.dto.CalorieRequest;
import com.sc.dto.CalorieResponse;
import com.sc.service.CalorieService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calorie")
public class CalorieController {
    private final CalorieService calorieService;

    @PostMapping("/add")
    public CalorieResponse add(Authentication authentication,
                               @RequestBody CalorieRequest calorieRequest) {
        return calorieService.add(authentication.getName(), calorieRequest);
    }

    @GetMapping("/getToday")
    public List<CalorieResponse> getToday(Authentication authentication){
        return calorieService.getToday(authentication.getName());
    }

    @GetMapping("/getAll")
    public List<CalorieResponse> all(Authentication auth) {
        return calorieService.getAll(auth.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id,
                       Authentication auth) {
        System.out.println("deleted");
        calorieService.delete(id, auth.getName());
    }

    @PutMapping("/update/{id}")
    public CalorieResponse update(@PathVariable Long id,
                                  Authentication auth,
                                  @RequestBody CalorieRequest request) {
        return calorieService.update(id, auth.getName(), request);
    }


    @GetMapping("/today/total")
    public int total(Authentication auth) {
        return calorieService.getTodayTotal(auth.getName());
    }

}
