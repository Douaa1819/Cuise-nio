package com.youcode.cuisenio.features.recipe.controller;

import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.request.RecipeIngredientRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.response.RecipeIngredientResponse;
import com.youcode.cuisenio.features.recipe.service.RecipeIngredientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/recipe-ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService service;

    public RecipeIngredientController(@Qualifier("recipeIngredientServiceImpl") RecipeIngredientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RecipeIngredientResponse> create(@Valid @RequestBody RecipeIngredientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeIngredientResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}