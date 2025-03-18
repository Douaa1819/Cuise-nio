package com.youcode.cuisenio.features.recipe.dto.recipe.response;

import com.youcode.cuisenio.features.auth.dto.admin.UserDto;
import com.youcode.cuisenio.features.recipe.dto.category.response.CategoryResponse;
import com.youcode.cuisenio.features.recipe.dto.comment.response.RecipeCommentResponse;
import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.response.RecipeIngredientResponse;
import com.youcode.cuisenio.features.recipe.dto.recipeStep.response.RecipeStepResponse;
import com.youcode.cuisenio.features.recipe.entity.Category;
import com.youcode.cuisenio.features.recipe.entity.DifficultyLevel;
import com.youcode.cuisenio.features.recipe.entity.RecipeComment;
import com.youcode.cuisenio.features.recipe.entity.RecipeStep;

import java.util.Date;
import java.util.List;

public record RecipeResponse(
        Long id,
        String title,
        String description,
        int cookingTime,
        String imageUrl,
        List<RecipeIngredientResponse> recipeIngredients,
        Date creationDate,
        Integer preparationTime,
        DifficultyLevel difficultyLevel,
        Integer servings,
        List<RecipeStepResponse> steps,
        List<RecipeCommentResponse> comments,
        CategoryResponse categorie,
        UserDto user
) {}