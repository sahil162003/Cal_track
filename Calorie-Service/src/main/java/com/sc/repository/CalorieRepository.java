package com.sc.repository;

import com.sc.entity.CalorieEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CalorieRepository extends JpaRepository<CalorieEntry, Long> {
    List<CalorieEntry> findByUserEmailAndDate(String name, LocalDate now);

    List<CalorieEntry> findByUserEmail(String name);
}
