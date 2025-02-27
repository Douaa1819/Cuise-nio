package com.youcode.cuisenio.features.recipe.dto.comment.response;

import lombok.Data;

import java.util.Date;


public record RecipeCommentResponse (
     Long id,
     String content,
     Long recipeId,
     String recipeTitle,
     Long userId,
     String username,
     Date createdAt,
     Date updatedAt,
     boolean isApproved
){}