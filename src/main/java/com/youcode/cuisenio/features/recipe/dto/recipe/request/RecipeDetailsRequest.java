package com.youcode.cuisenio.features.recipe.dto.recipe.request;

import com.youcode.cuisenio.features.recipe.dto.ingredient.response.request.RecipeIngredientRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeStep.request.RecipeStepRequest;

import java.util.List;

public record RecipeDetailsRequest(
        List<RecipeIngredientRequest> ingredients,
        List<RecipeStepRequest> steps
) {
}
