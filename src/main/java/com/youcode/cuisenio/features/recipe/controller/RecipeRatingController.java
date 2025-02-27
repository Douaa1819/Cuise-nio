package com.youcode.cuisenio.features.recipe.controller;

import com.youcode.cuisenio.features.recipe.dto.recipeRating.request.RecipeRatingRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeRating.response.RecipeRatingResponse;
import com.youcode.cuisenio.features.recipe.service.impl.RecipeRatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes/{recipeId}/ratings")
public class RecipeRatingController {

    private final RecipeRatingService ratingService;

    public RecipeRatingController(RecipeRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RecipeRatingResponse> addRating(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long recipeId,
            @Valid @RequestBody RecipeRatingRequest request) {
        Long userId = extractUserId(userDetails);
        RecipeRatingResponse response = ratingService.addRating(userId, recipeId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RecipeRatingResponse> updateRating(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long recipeId,
            @Valid @RequestBody RecipeRatingRequest request) {
        Long userId = extractUserId(userDetails);
        RecipeRatingResponse response = ratingService.updateRating(userId, recipeId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRating(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long recipeId) {
        Long userId = extractUserId(userDetails);
        ratingService.deleteRating(userId, recipeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<RecipeRatingResponse> getUserRatingForRecipe(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long recipeId) {
        Long userId = extractUserId(userDetails);
        RecipeRatingResponse response = ratingService.getUserRatingForRecipe(userId, recipeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RecipeRatingResponse>> getAllRatingsForRecipe(
            @PathVariable Long recipeId) {
        List<RecipeRatingResponse> ratings = ratingService.getRatingsByRecipeId(recipeId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getRatingStats(
            @PathVariable Long recipeId) {
        Double average = ratingService.getAverageRatingForRecipe(recipeId);
        Integer total = ratingService.getTotalRatingsForRecipe(recipeId);

        Map<String, Object> stats = Map.of(
                "averageRating", average != null ? average : 0,
                "totalRatings", total != null ? total : 0
        );

        return ResponseEntity.ok(stats);
    }

    private Long extractUserId(UserDetails userDetails) {
        return Long.valueOf(userDetails.getUsername());
    }
}