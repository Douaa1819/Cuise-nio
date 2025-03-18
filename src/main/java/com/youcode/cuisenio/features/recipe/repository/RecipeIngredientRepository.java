package com.youcode.cuisenio.features.recipe.repository;

import com.youcode.cuisenio.features.recipe.entity.Recipe;
import com.youcode.cuisenio.features.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
 public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
 void deleteByRecipe(Recipe recipe);

}
