package com.youcode.cuisenio.features.recipe.dto.recipeIngredient.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecipeIngredientRequest(
        @NotNull(message = "recipeId cannot be null")
        Long recipeId,

        @NotNull(message = "ingredientId cannot be null")
        Long ingredientId,

        @NotNull(message = "quantity cannot be null")
        Double quantity,

        @NotBlank(message = "unit cannot be blank")
        String unit
) {}
