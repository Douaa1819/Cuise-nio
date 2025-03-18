package com.youcode.cuisenio.features.recipe.dto.recipeIngredient.response;

import com.youcode.cuisenio.features.recipe.dto.ingredient.response.IngredientResponse;

public record RecipeIngredientResponse(
        Long id,
        Double quantity,
        Long recipeId,
        String unit,
        IngredientResponse ingredient
) {}