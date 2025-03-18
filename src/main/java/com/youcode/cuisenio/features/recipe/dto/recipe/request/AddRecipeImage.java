package com.youcode.cuisenio.features.recipe.dto.recipe.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record AddRecipeImage(
        @NotNull
        MultipartFile imageUrl
) {
}
