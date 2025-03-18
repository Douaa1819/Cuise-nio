package com.youcode.cuisenio.features.recipe.service;

import com.youcode.cuisenio.common.service.CrudService;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.AddRecipeImage;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeDetailsRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.response.RecipeResponse;
import com.youcode.cuisenio.features.recipe.entity.DifficultyLevel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface RecipeService extends CrudService<Long, RecipeRequest, RecipeResponse> {
     RecipeResponse createRecipe(String email, RecipeRequest request);
     RecipeResponse addImage(Long id, AddRecipeImage recipeImage);
     Page<RecipeResponse> getRecipesByUser(String email, Pageable pageable);
     RecipeResponse updateRecipe(Long recipeId, String email, RecipeRequest request);
     void deleteRecipe(Long recipeId, String email);
     Page<RecipeResponse> searchRecipes(
             String query,
             Long categoryId,
             DifficultyLevel difficulty,
             Integer maxPrepTime,
             Integer maxCookTime,
             Pageable pageable
     );
}

