package com.youcode.cuisenio.features.recipe.controller;

import com.youcode.cuisenio.features.recipe.dto.comment.request.RecipeCommentRequest;
import com.youcode.cuisenio.features.recipe.dto.comment.response.RecipeCommentResponse;
import com.youcode.cuisenio.features.recipe.service.impl.RecipeCommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes/{recipeId}/comments")
public class RecipeCommentController {

    private final RecipeCommentService commentService;

    public RecipeCommentController(RecipeCommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<RecipeCommentResponse> addComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long recipeId,
            @Valid @RequestBody RecipeCommentRequest request) {
        //Long userId = extractUserId(userDetails);
        Long userId=1L;
        RecipeCommentResponse response = commentService.addComment(userId, recipeId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<RecipeCommentResponse> updateComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long commentId,
            @Valid @RequestBody RecipeCommentRequest request) {
        Long userId=1L;

       // Long userId = extractUserId(userDetails);

        return null;
    }
}