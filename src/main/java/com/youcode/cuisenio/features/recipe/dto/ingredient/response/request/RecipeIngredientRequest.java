package com.youcode.cuisenio.features.recipe.dto.ingredient.response.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecipeIngredientRequest(
        @NotNull
        Long ingredientId,

        Long recipeId,

        @NotNull
        Double quantity,

        @NotBlank
        String unit
) {}

