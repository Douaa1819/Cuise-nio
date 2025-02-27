package com.youcode.cuisenio.features.recipe.service.impl;

import com.youcode.cuisenio.features.auth.entity.User;
import com.youcode.cuisenio.features.auth.exception.UserNotFoundException;
import com.youcode.cuisenio.features.auth.repository.UserRepository;
import com.youcode.cuisenio.features.recipe.dto.recipeRating.request.RecipeRatingRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeRating.response.RecipeRatingResponse;
import com.youcode.cuisenio.features.recipe.entity.Recipe;
import com.youcode.cuisenio.features.recipe.entity.RecipeRating;
import com.youcode.cuisenio.features.recipe.exception.RatingAlreadyExistsException;
import com.youcode.cuisenio.features.recipe.exception.RecipeNotFoundException;
import com.youcode.cuisenio.features.recipe.exception.RecipeRatingNotFoundException;
import com.youcode.cuisenio.features.recipe.mapper.RecipeRatingMapper;
import com.youcode.cuisenio.features.recipe.repository.RecipeRatingRepository;
import com.youcode.cuisenio.features.recipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeRatingService {
    private final RecipeRatingRepository recipeRatingRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeRatingMapper recipeRatingMapper;

    public RecipeRatingService(RecipeRatingRepository recipeRatingRepository,
                               RecipeRepository recipeRepository,
                               UserRepository userRepository,
                               RecipeRatingMapper recipeRatingMapper) {
        this.recipeRatingRepository = recipeRatingRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.recipeRatingMapper = recipeRatingMapper;
    }

    public RecipeRatingResponse addRating(Long userId, Long recipeId, RecipeRatingRequest request) {
        Optional<RecipeRating> existingRating = recipeRatingRepository.findByRecipeIdAndUserId(recipeId, userId);
        if (existingRating.isPresent()) {
            throw new RatingAlreadyExistsException("User has already rated this recipe");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + recipeId));

        RecipeRating rating = new RecipeRating();
        rating.setScore(request.score());
        rating.setCreatedAt(new Date());
        rating.setRecipe(recipe);
        rating.setUser(user);

        RecipeRating savedRating = recipeRatingRepository.save(rating);
        return recipeRatingMapper.toResponse(savedRating);
    }

    public RecipeRatingResponse updateRating(Long userId, Long recipeId, RecipeRatingRequest request) {
        RecipeRating rating = recipeRatingRepository.findByRecipeIdAndUserId(recipeId, userId)
                .orElseThrow(() -> new RecipeRatingNotFoundException("Rating not found for this user and recipe"));

        rating.setScore(request.score());
        RecipeRating updatedRating = recipeRatingRepository.save(rating);
        return recipeRatingMapper.toResponse(updatedRating);
    }

    public void deleteRating(Long userId, Long recipeId) {
        RecipeRating rating = recipeRatingRepository.findByRecipeIdAndUserId(recipeId, userId)
                .orElseThrow(() -> new RecipeRatingNotFoundException("Rating not found for this user and recipe"));

        recipeRatingRepository.delete(rating);
    }

    public RecipeRatingResponse getUserRatingForRecipe(Long userId, Long recipeId) {
        RecipeRating rating = recipeRatingRepository.findByRecipeIdAndUserId(recipeId, userId)
                .orElseThrow(() -> new RecipeRatingNotFoundException("Rating not found for this user and recipe"));

        return recipeRatingMapper.toResponse(rating);
    }

    public List<RecipeRatingResponse> getRatingsByRecipeId(Long recipeId) {
        List<RecipeRating> ratings = recipeRatingRepository.findByRecipeId(recipeId);
        return ratings.stream()
                .map(recipeRatingMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Double getAverageRatingForRecipe(Long recipeId) {
        return recipeRatingRepository.getAverageRatingByRecipeId(recipeId);
    }

    public Integer getTotalRatingsForRecipe(Long recipeId) {
        return recipeRatingRepository.countByRecipeId(recipeId);
    }
}
