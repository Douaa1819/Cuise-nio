package com.youcode.cuisenio.features.mealplan.dto.request;

import com.youcode.cuisenio.features.mealplan.entity.MealType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;

public record MealPlannerRequest (
    @NotNull(message = "Recipe ID is required")
     Long recipeId,

    @NotNull(message = "Planning date is required")
     LocalDate planningDate,

    @NotNull(message = "Day of week is required")
     DayOfWeek dayOfWeek,

    @NotNull(message = "Meal type is required")
     MealType mealType,

    @Min(value = 1, message = "Number of servings must be at least 1")
     Integer servings,

     String notes
    ){
}
