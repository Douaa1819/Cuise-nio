package com.youcode.cuisenio.features.recipe.repository;

import com.youcode.cuisenio.features.recipe.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
