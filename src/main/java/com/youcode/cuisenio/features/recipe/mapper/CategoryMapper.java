package com.youcode.cuisenio.features.recipe.mapper;

import com.youcode.cuisenio.features.recipe.dto.category.request.CategoryRequest;
import com.youcode.cuisenio.features.recipe.dto.category.response.CategoryResponse;
import com.youcode.cuisenio.features.recipe.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest categoryRequest);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryResponse toResponse(Category category);

}
