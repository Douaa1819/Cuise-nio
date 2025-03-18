package com.youcode.cuisenio.features.recipe.controller;

import com.youcode.cuisenio.features.recipe.dto.recipe.request.AddRecipeImage;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.response.RecipeResponse;
import com.youcode.cuisenio.features.recipe.entity.DifficultyLevel;
import com.youcode.cuisenio.features.recipe.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody RecipeRequest request
    ) {
        String email = userDetails.getUsername();
        RecipeResponse response = recipeService.createRecipe(email, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(value = "/add-image/{recipeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeResponse> addImage(
            @PathVariable Long recipeId,
            @Valid @ModelAttribute AddRecipeImage recipeImage
    ) {
        return ResponseEntity.ok(recipeService.addImage(recipeId, recipeImage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<RecipeResponse>> getAllRecipes(
            @PageableDefault(size = 10, sort = "creationDate") Pageable pageable
    ) {
        return ResponseEntity.ok(recipeService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RecipeResponse>> searchRecipes(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) DifficultyLevel difficulty,
            @RequestParam(required = false) Integer maxPrepTime,
            @RequestParam(required = false) Integer maxCookTime,
            @PageableDefault(size = 10, sort = "creationDate") Pageable pageable
    ) {
        return ResponseEntity.ok(recipeService.searchRecipes(
                query, categoryId, difficulty, maxPrepTime, maxCookTime, pageable
        ));
    }

    @GetMapping("/my-recipes")
    public ResponseEntity<Page<RecipeResponse>> getMyRecipes(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 10, sort = "creationDate") Pageable pageable
    ) {
        String email = userDetails.getUsername();
        return ResponseEntity.ok(recipeService.getRecipesByUser(email, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody RecipeRequest request
    ) {
        String email = userDetails.getUsername();
        return ResponseEntity.ok(recipeService.updateRecipe(id, email, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        recipeService.deleteRecipe(id, email);
        return ResponseEntity.noContent().build();
    }
}