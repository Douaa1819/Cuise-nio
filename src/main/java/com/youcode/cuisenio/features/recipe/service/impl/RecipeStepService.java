package com.youcode.cuisenio.features.recipe.service.impl;

import com.youcode.cuisenio.features.recipe.dto.recipeStep.request.RecipeStepRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeStep.response.RecipeStepResponse;
import com.youcode.cuisenio.features.recipe.entity.Recipe;
import com.youcode.cuisenio.features.recipe.entity.RecipeStep;
import com.youcode.cuisenio.features.recipe.exception.RecipeNotFoundException;
import com.youcode.cuisenio.features.recipe.mapper.RecipeStepMapper;
import com.youcode.cuisenio.features.recipe.repository.RecipeRepository;
import com.youcode.cuisenio.features.recipe.repository.RecipeStepRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeStepService {
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeStepMapper recipeStepMapper;

    public RecipeStepService(RecipeStepRepository recipeStepRepository,
                             RecipeRepository recipeRepository,
                             RecipeStepMapper recipeStepMapper) {
        this.recipeStepRepository = recipeStepRepository;
        this.recipeRepository = recipeRepository;
        this.recipeStepMapper = recipeStepMapper;
    }

    public List<RecipeStepResponse> createSteps(Long recipeId, List<RecipeStepRequest> requests) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + recipeId));

        List<RecipeStep> steps = requests.stream()
                .map(request -> {
                    RecipeStep step = recipeStepMapper.toEntity(request);
                    step.setRecipe(recipe);
                    return step;
                })
                .collect(Collectors.toList());

        steps = recipeStepRepository.saveAll(steps);
        return steps.stream()
                .map(recipeStepMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<RecipeStepResponse> getStepsByRecipeId(Long recipeId) {
        return recipeStepRepository.findByRecipeIdOrderByStepNumberAsc(recipeId)
                .stream()
                .map(recipeStepMapper::toResponse)
                .collect(Collectors.toList());
    }
}
