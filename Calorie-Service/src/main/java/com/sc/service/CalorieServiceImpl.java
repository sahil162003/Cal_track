package com.sc.service;

import com.sc.dto.CalorieRequest;
import com.sc.dto.CalorieResponse;
import com.sc.entity.CalorieEntry;
import com.sc.repository.CalorieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class CalorieServiceImpl implements CalorieService{

    private final CalorieRepository calorieRepository;

    @Override
    public CalorieResponse add(String name, CalorieRequest calorieRequest) {
        CalorieEntry entry = CalorieEntry.builder()
                .userEmail(name)
                .foodName(calorieRequest.getFoodName())
                .calories(calorieRequest.getCalories())
                .date(LocalDate.now())
                .build();

        return mapToResponse(calorieRepository.save(entry));
    }

    @Override
    public List<CalorieResponse> getToday(String name) {

        return calorieRepository
                .findByUserEmailAndDate(name, LocalDate.now())
                .stream()
                .map(entry -> mapToResponse(entry))
                .toList();
    }

    @Override
    public List<CalorieResponse> getAll(String name) {

        return calorieRepository
                .findByUserEmail(name)
                .stream()
                .map(entry -> mapToResponse(entry))
                .toList();
    }

    @Override
    public int getTodayTotal(String name) {
        return calorieRepository
                .findByUserEmailAndDate(name, LocalDate.now())
                .stream()
                .mapToInt(CalorieEntry::getCalories)
                .sum();
    }

    @Override
    public void delete(Long id, String name) {
        CalorieEntry entry = calorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        if (!entry.getUserEmail().equals(name)) {
            throw new RuntimeException("Not allowed");
        }

        calorieRepository.delete(entry);

    }

    @Override
    public CalorieResponse update(Long id, String name, CalorieRequest request) {
        CalorieEntry entry = calorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        if (!entry.getUserEmail().equals(name)) {
            throw new RuntimeException("Not allowed");
        }

        entry.setFoodName(request.getFoodName());
        entry.setCalories(request.getCalories());

        return mapToResponse(calorieRepository.save(entry));
    }


    private CalorieResponse mapToResponse(CalorieEntry save) {
        return CalorieResponse.builder()
                .id(save.getId())
                .foodName(save.getFoodName())
                .calories(save.getCalories())
                .date(save.getDate())
                .build();
    }
}
