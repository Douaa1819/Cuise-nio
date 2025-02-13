package com.youcode.cuisenio.recipe.repository;

import com.youcode.cuisenio.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUserId(Long userId);
}
