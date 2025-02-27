package com.youcode.cuisenio.features.mealplan.mapper;

import com.youcode.cuisenio.features.mealplan.dto.request.MealPlannerRequest;
import com.youcode.cuisenio.features.mealplan.dto.response.MealPlannerResponse;
import com.youcode.cuisenio.features.mealplan.entity.MealPlanner;
import com.youcode.cuisenio.features.recipe.mapper.RecipeMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {RecipeMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MealPlannerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "dayOfWeek", ignore = true)
    MealPlanner toEntity(MealPlannerRequest request);

  //  @Mapping(target = "recipe", source = "recipe")
   // @Mapping(target = "userId", source = "user.id")
    MealPlannerResponse toResponse(MealPlanner mealPlanner);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    @Mapping(target="dayOfWeek", ignore = true)
    void updateEntityFromRequest(MealPlannerRequest request, @MappingTarget MealPlanner mealPlanner);
}
