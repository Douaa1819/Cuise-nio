package com.youcode.cuisenio.features.recipe.service.impl;

import com.youcode.cuisenio.features.auth.entity.User;
import com.youcode.cuisenio.features.auth.exception.UserNotFoundException;
import com.youcode.cuisenio.features.auth.repository.UserRepository;
import com.youcode.cuisenio.features.recipe.dto.comment.request.RecipeCommentRequest;
import com.youcode.cuisenio.features.recipe.dto.comment.response.RecipeCommentResponse;
import com.youcode.cuisenio.features.recipe.entity.Recipe;
import com.youcode.cuisenio.features.recipe.entity.RecipeComment;
import com.youcode.cuisenio.features.recipe.exception.RecipeCommentNotFoundException;
import com.youcode.cuisenio.features.recipe.exception.RecipeNotFoundException;
import com.youcode.cuisenio.features.recipe.exception.UnauthorizedCommentModificationException;
import com.youcode.cuisenio.features.recipe.mapper.RecipeCommentMapper;
import com.youcode.cuisenio.features.recipe.repository.RecipeCommentRepository;
import com.youcode.cuisenio.features.recipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class RecipeCommentService {
    private final RecipeCommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipeCommentMapper commentMapper;

    public RecipeCommentService(RecipeCommentRepository commentRepository,
                                RecipeRepository recipeRepository,
                                UserRepository userRepository,
                                RecipeCommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    public RecipeCommentResponse addComment(Long userId, Long recipeId, RecipeCommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + recipeId));

        RecipeComment comment = new RecipeComment();
        comment.setContent(request.content());
        comment.setCreatedAt(new Date());
        comment.setApproved(false);
        comment.setRecipe(recipe);
        comment.setUser(user);

        RecipeComment savedComment = commentRepository.save(comment);
        return commentMapper.toResponse(savedComment);
    }

    public RecipeCommentResponse updateComment(Long userId, Long commentId, RecipeCommentRequest request) {
        RecipeComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RecipeCommentNotFoundException("Comment not found with id: " + commentId));

        // Verify the user is the owner of the comment
        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedCommentModificationException("User is not authorized to modify this comment");
        }

        comment.setContent(request.content());
        comment.setUpdatedAt(new Date());
        // Reset approval when comment is updated
        comment.setApproved(false);

        RecipeComment updatedComment = commentRepository.save(comment);
        return commentMapper.toResponse(updatedComment);
    }

    public void deleteComment(Long userId, Long commentId) {
        RecipeComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RecipeCommentNotFoundException("Comment not found with id: " + commentId));

        // Verify the user is the owner of the comment
        if (!comment.getUser().getId().equals(userId)) {
            throw new UnauthorizedCommentModificationException("User is not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

    public Page<RecipeCommentResponse> getApprovedCommentsByRecipeId(Long recipeId, Pageable pageable) {
        Page<RecipeComment> comments = commentRepository.findByRecipeIdAndIsApprovedOrderByCreatedAtDesc(recipeId, true, pageable);
        return comments.map(commentMapper::toResponse);
    }

    // Admin functions
    public RecipeCommentResponse approveComment(Long commentId) {
        RecipeComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RecipeCommentNotFoundException("Comment not found with id: " + commentId));

        comment.setApproved(true);
        RecipeComment approvedComment = commentRepository.save(comment);
        return commentMapper.toResponse(approvedComment);
    }

    public Page<RecipeCommentResponse> getPendingComments(Pageable pageable) {
        Page<RecipeComment> pendingComments = commentRepository.findByIsApproved(false, pageable);
        return pendingComments.map(commentMapper::toResponse);
    }
}
