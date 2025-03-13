package com.youcode.cuisenio.features.recipe.service;

import com.youcode.cuisenio.common.service.CrudService;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeDetailsRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.response.RecipeResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface RecipeService extends CrudService<Long, RecipeRequest, RecipeResponse> {
     RecipeResponse createRecipe(String email, RecipeRequest request, RecipeDetailsRequest detailsRequest);
}

