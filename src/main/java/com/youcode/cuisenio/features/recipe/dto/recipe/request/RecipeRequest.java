package com.youcode.cuisenio.features.recipe.dto.recipe.request;

import com.youcode.cuisenio.features.recipe.entity.DifficultyLevel;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public record RecipeRequest (
        @NotBlank(message = "Recipe title is required")
         String title,

        @NotBlank(message = "Recipe description is required")
         String description,

        @NotNull(message = "Difficulty level is required")
         DifficultyLevel difficultyLevel,

        @Min(value = 1, message = "Preparation time must be at least 1 minute")
         Integer preparationTime,

        @Min(value = 0, message = "Cooking time cannot be negative")
         Integer cookingTime,

        @Min(value = 1, message = "Number of servings must be at least 1")
         Integer servings,

         MultipartFile imageUrl,

         List<Long> categoryIds

){}
