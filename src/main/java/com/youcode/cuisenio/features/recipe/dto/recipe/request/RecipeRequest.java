package com.youcode.cuisenio.features.recipe.dto.recipe.request;

import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.request.RecipeIngredientRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RecipeRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        String title,

        @NotBlank
        String description,

        @NotNull
        Integer cookingTime,

        @NotEmpty
        List<RecipeIngredientRequest> ingredients
) {}
