package com.sc.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CalorieResponse {
    private Long id;        // needed
    private String foodName;
    private int calories;
    private LocalDate date;
}
