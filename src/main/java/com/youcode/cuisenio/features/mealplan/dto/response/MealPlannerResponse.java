package com.youcode.cuisenio.features.mealplan.dto.response;

import com.youcode.cuisenio.features.mealplan.entity.MealType;
import com.youcode.cuisenio.features.recipe.dto.recipe.response.RecipeResponse;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class MealPlannerResponse {
    private Long id;
    private RecipeResponse recipe;
    private LocalDate planningDate;
    private DayOfWeek dayOfWeek;
    private MealType mealType;
    private Integer servings;
    private String notes;
    private Long userId;
}