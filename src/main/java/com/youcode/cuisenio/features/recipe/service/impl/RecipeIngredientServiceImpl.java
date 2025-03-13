package com.youcode.cuisenio.features.recipe.service.impl;

import com.youcode.cuisenio.features.recipe.dto.ingredient.response.request.RecipeIngredientRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.response.RecipeIngredientResponse;
import com.youcode.cuisenio.features.recipe.entity.Ingredient;
import com.youcode.cuisenio.features.recipe.entity.Recipe;
import com.youcode.cuisenio.features.recipe.entity.RecipeIngredient;
import com.youcode.cuisenio.features.recipe.exception.IngredientNotFoundException;
import com.youcode.cuisenio.features.recipe.mapper.RecipeIngredientMapper;
import com.youcode.cuisenio.features.recipe.mapper.RecipeIngredientMapperImpl;
import com.youcode.cuisenio.features.recipe.repository.IngredientRepository;
import com.youcode.cuisenio.features.recipe.repository.RecipeIngredientRepository;
import com.youcode.cuisenio.features.recipe.repository.RecipeRepository;
import com.youcode.cuisenio.features.recipe.service.RecipeIngredientService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientMapper recipeIngredientMapper;

    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository, RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeIngredientMapper recipeIngredientMapper, RecipeIngredientMapperImpl recipeIngredientMapperImpl) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    public RecipeIngredientResponse create(RecipeIngredientRequest request) {
        Recipe recipe = recipeRepository.findById(request.recipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        Ingredient ingredient = ingredientRepository.findById(request.ingredientId())
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        RecipeIngredient entity = recipeIngredientMapper.toEntity(request);
        entity.setRecipe(recipe);
        entity.setIngredient(ingredient);

        return recipeIngredientMapper.toResponse(recipeIngredientRepository.save(entity));
    }
    @Override
    public Page<RecipeIngredientResponse> findAll(Pageable pageable) {
        return recipeIngredientRepository.findAll(pageable).map(recipeIngredientMapper::toResponse);
    }

    @Override
    public RecipeIngredientResponse findById(Long aLong) {
        return recipeIngredientMapper.toResponse(recipeIngredientRepository.findById(aLong).orElseThrow(() -> new IngredientNotFoundException("Ingrédient non trouvé avec l'id: " + aLong)));
    }



    @Override
    public RecipeIngredientResponse update(Long aLong, RecipeIngredientRequest recipeIngredientRequest) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(aLong).orElseThrow(() -> new IngredientNotFoundException("Ingrédient non trouvé avec l'id: " + aLong));

         recipeIngredient=recipeIngredientRepository.save(recipeIngredient);
        return null;

    }

    @Override
    public void delete(Long aLong) {

    }
}
