package com.youcode.cuisenio.features.mealplan.service;

import com.youcode.cuisenio.features.mealplan.dto.request.MealPlannerRequest;
import com.youcode.cuisenio.features.mealplan.dto.response.MealPlannerResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MealPlannerService {
    MealPlannerResponse createMealPlan(Long recipeId, MealPlannerRequest request, String email);
    List<MealPlannerResponse> getMealPlansByUser(String email);
    MealPlannerResponse updateMealPlan(Long id, MealPlannerRequest request);
    void deleteMealPlan(Long id);
}
