package com.youcode.cuisenio.features.recipe.service;

import com.youcode.cuisenio.common.service.CrudService;
import com.youcode.cuisenio.features.recipe.dto.ingredient.response.request.RecipeIngredientRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.response.RecipeIngredientResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface RecipeIngredientService extends CrudService<Long, RecipeIngredientRequest, RecipeIngredientResponse> {
}
