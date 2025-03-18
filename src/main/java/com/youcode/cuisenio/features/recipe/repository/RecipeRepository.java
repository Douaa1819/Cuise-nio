package com.youcode.cuisenio.features.recipe.repository;

import com.youcode.cuisenio.features.auth.entity.User;
import com.youcode.cuisenio.features.recipe.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> , JpaSpecificationExecutor<Recipe> {
    Page<Recipe> findByUser(User user, Pageable pageable);
}
