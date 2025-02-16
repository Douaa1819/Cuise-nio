package com.youcode.cuisenio.features.recipe.service.impl;

import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.response.RecipeResponse;
import com.youcode.cuisenio.features.recipe.entity.Recipe;
import com.youcode.cuisenio.features.recipe.exception.RecipeNotFoundException;
import com.youcode.cuisenio.features.recipe.mapper.RecipeMapper;
import com.youcode.cuisenio.features.recipe.repository.RecipeRepository;
import com.youcode.cuisenio.features.recipe.service.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeMapper recipeMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeMapper = recipeMapper;

    }
    @Override
    public Page<RecipeResponse> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable).map(recipeMapper::toResponse);
    }

    @Override
    public RecipeResponse findById(Long id) {
        return recipeRepository.findById(id).map(recipeMapper::toResponse)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe non trouvé avec l'id: " + id));
    }

    @Override
    public RecipeResponse create(RecipeRequest recipeRequest) {
        Recipe recipe = recipeMapper.toEntity(recipeRequest);
        recipe = recipeRepository.save(recipe);
        return recipeMapper.toResponse(recipe);
    }

    @Override
    public RecipeResponse update(Long id, RecipeRequest recipeRequest) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(()-> new RecipeNotFoundException("Recipe Not Found with id : "+id));
        return recipeMapper.toResponse(recipeRepository.save(recipe));
    }

    @Override
    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException("Recipe Not Found with id : "+id);
        }
        recipeRepository.deleteById(id);
    }
}
