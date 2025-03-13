package com.youcode.cuisenio.features.recipe.mapper;

import com.youcode.cuisenio.common.mapper.BaseMapper;
import com.youcode.cuisenio.features.recipe.dto.ingredient.response.request.RecipeIngredientRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.response.RecipeIngredientResponse;
import com.youcode.cuisenio.features.recipe.entity.RecipeIngredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper extends BaseMapper<RecipeIngredient, RecipeIngredientRequest, RecipeIngredientResponse> {}

