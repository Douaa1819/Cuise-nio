package com.youcode.cuisenio.features.mealplan.repository;

import com.youcode.cuisenio.features.mealplan.entity.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByUserId(Long userId);
}