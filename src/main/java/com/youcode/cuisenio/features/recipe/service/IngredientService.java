package com.youcode.cuisenio.features.recipe.service;

import com.youcode.cuisenio.features.recipe.dto.ingredient.request.IngredientRequest;
import com.youcode.cuisenio.features.recipe.dto.ingredient.response.IngredientResponse;
import com.youcode.cuisenio.features.recipe.entity.Ingredient;
import com.youcode.cuisenio.features.recipe.exception.IngredientNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface IngredientService {

    public Page<IngredientResponse> findAll(Pageable pageable);

    public IngredientResponse findById(Long id);

    public IngredientResponse create(IngredientRequest request) ;

    public IngredientResponse update(Long id, IngredientRequest request) ;

    public void delete(Long id) ;
}
