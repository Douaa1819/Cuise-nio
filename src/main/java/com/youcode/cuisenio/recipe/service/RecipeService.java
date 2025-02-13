package com.youcode.cuisenio.recipe.service;

import com.youcode.cuisenio.recipe.entity.Recipe;
import com.youcode.cuisenio.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> findByUserId(Long userId) {
        return recipeRepository.findByUserId(userId);
    }

    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}
