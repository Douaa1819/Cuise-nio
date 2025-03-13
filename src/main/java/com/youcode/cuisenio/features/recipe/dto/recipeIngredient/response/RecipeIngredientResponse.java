package com.youcode.cuisenio.features.recipe.dto.recipeIngredient.response;

public record RecipeIngredientResponse(
        Long id,
        String ingredientName,
        Double quantity,
        Long recipeId,
        String unit
) {}