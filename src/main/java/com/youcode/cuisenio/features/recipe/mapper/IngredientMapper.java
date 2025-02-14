package com.youcode.cuisenio.features.recipe.mapper;

import com.youcode.cuisenio.features.recipe.dto.ingredient.request.IngredientRequest;
import com.youcode.cuisenio.features.recipe.dto.ingredient.response.IngredientResponse;
import com.youcode.cuisenio.features.recipe.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface IngredientMapper {
    Ingredient toEntity(IngredientRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    IngredientResponse toResponse(Ingredient ingredient);
}
