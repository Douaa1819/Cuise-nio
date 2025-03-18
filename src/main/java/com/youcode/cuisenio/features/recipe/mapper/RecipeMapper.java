package com.youcode.cuisenio.features.recipe.mapper;

import com.youcode.cuisenio.common.mapper.BaseMapper;
import com.youcode.cuisenio.features.recipe.dto.category.response.CategoryResponse;
import com.youcode.cuisenio.features.recipe.dto.comment.response.RecipeCommentResponse;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.response.RecipeResponse;
import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.response.RecipeIngredientResponse;
import com.youcode.cuisenio.features.recipe.dto.recipeRating.response.RecipeRatingResponse;
import com.youcode.cuisenio.features.recipe.dto.recipeStep.response.RecipeStepResponse;
import com.youcode.cuisenio.features.recipe.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecipeMapper extends BaseMapper<Recipe, RecipeRequest, RecipeResponse> {

    @Override
    @Mapping(target = "categorie", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "imageUrl",ignore = true)
    Recipe toEntity(RecipeRequest request);
}