package com.youcode.cuisenio.features.recipe.service;

import com.youcode.cuisenio.features.recipe.entity.Recipe;
import com.youcode.cuisenio.features.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecipeService {

    public List<Recipe> findByUserId(Long userId) ;

    public Recipe save(Recipe recipe) ;
}
