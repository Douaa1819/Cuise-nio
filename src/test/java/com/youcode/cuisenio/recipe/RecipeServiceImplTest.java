package com.youcode.cuisenio.recipe;

import com.youcode.cuisenio.features.auth.dto.admin.UserDto;
import com.youcode.cuisenio.features.auth.entity.User;
import com.youcode.cuisenio.features.auth.repository.UserRepository;
import com.youcode.cuisenio.features.recipe.dto.category.response.CategoryResponse;
import com.youcode.cuisenio.features.recipe.dto.ingredient.response.IngredientResponse;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.response.RecipeResponse;
import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.request.RecipeIngredientRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeIngredient.response.RecipeIngredientResponse;
import com.youcode.cuisenio.features.recipe.dto.recipeStep.request.RecipeStepRequest;
import com.youcode.cuisenio.features.recipe.dto.recipeStep.response.RecipeStepResponse;
import com.youcode.cuisenio.features.recipe.entity.*;
import com.youcode.cuisenio.features.recipe.exception.*;
import com.youcode.cuisenio.features.recipe.mapper.RecipeMapper;
import com.youcode.cuisenio.features.recipe.repository.*;
import com.youcode.cuisenio.features.recipe.service.impl.FileStorageService;
import com.youcode.cuisenio.features.recipe.service.impl.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock private RecipeRepository recipeRepository;
    @Mock private IngredientRepository ingredientRepository;
    @Mock private UserRepository userRepository;
    @Mock private RecipeMapper recipeMapper;
    @Mock private FileStorageService fileStorageService;
    @Mock private RecipeIngredientRepository recipeIngredientRepository;
    @Mock private RecipeStepRepository recipeStepRepository;
    @Mock private CategoryRepository categoryRepository;

    @InjectMocks private RecipeServiceImpl recipeService;

    private RecipeRequest recipeRequest;
    private Recipe recipe;
    private RecipeResponse recipeResponse;
    private User user;
    private Category category;
    private LocalDateTime registrationDate;

    @BeforeEach
    void setUp() {
        registrationDate = LocalDateTime.now();

        user = new User();
        user.setId(1L);
        user.setEmail("chef@cuisenio.com");

        category = new Category();
        category.setId(1L);
        category.setName("Desserts");

        recipeRequest = new RecipeRequest(
                "Tarte aux pommes",
                "Délicieuse tarte traditionnelle",
                DifficultyLevel.INTERMEDIATE,
                30,
                45,
                6,
                1L,
                List.of(new RecipeIngredientRequest(1L, 1L, 1.5, "kg")),
                List.of(new RecipeStepRequest(1, "Préparer la pâte"))
        );

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setTitle("Tarte aux pommes");
        recipe.setDescription("Délicieuse tarte traditionnelle");
        recipe.setPreparationTime(30);
        recipe.setCookingTime(45);
        recipe.setServings(6);
        recipe.setDifficultyLevel(DifficultyLevel.INTERMEDIATE);
        recipe.setUser(user);
        recipe.setCategorie(category);
        recipe.setCreationDate(new Date());

        IngredientResponse ingredientResponse = new IngredientResponse(1L, "Pommes");

        recipeResponse = new RecipeResponse(
                1L,
                "Tarte aux pommes",
                "Délicieuse tarte traditionnelle",
                45,
                "tarte.jpg",
                List.of(new RecipeIngredientResponse(1L, 1.5, 1L, "kg", ingredientResponse)),
                recipe.getCreationDate(),
                30,
                DifficultyLevel.INTERMEDIATE,
                6,
                List.of(new RecipeStepResponse(1L, 1, "Préparer la pâte")),
                Collections.emptyList(),
                new CategoryResponse(category.getId(), category.getName()),
                new UserDto(user.getId(), "ChefName", "ChefLastName", user.getEmail(), false, registrationDate)
        );
    }

    @Test
    void createRecipe_ShouldSuccess() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeMapper.toResponse(any(Recipe.class))).thenReturn(recipeResponse);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));

        RecipeResponse response = recipeService.createRecipe("chef@cuisenio.com", recipeRequest);

        assertNotNull(response);
        assertEquals("Tarte aux pommes", response.title());
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void findById_ShouldReturnRecipe() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(recipeMapper.toResponse(recipe)).thenReturn(recipeResponse);

        RecipeResponse response = recipeService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
    }

    @Test
    void updateRecipe_ShouldUpdateCorrectly() {
        RecipeRequest updateRequest = new RecipeRequest(
                "Tarte améliorée",
                "Nouvelle version",
                DifficultyLevel.ADVANCED,
                40,
                50,
                8,
                1L,
                List.of(new RecipeIngredientRequest(1L, 2L, 2.0, "kg")),
                List.of(new RecipeStepRequest(2, "Cuire plus longtemps"))
        );

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeMapper.toResponse(any(Recipe.class))).thenReturn(recipeResponse);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(2L);
        when(ingredientRepository.findById(2L)).thenReturn(Optional.of(ingredient));

        RecipeResponse response = recipeService.updateRecipe(1L, "chef@cuisenio.com", updateRequest);

        assertNotNull(response);
        verify(recipeIngredientRepository).deleteByRecipe(recipe);
        verify(recipeStepRepository).deleteByRecipe(recipe);
    }

    @Test
    void searchRecipes_ShouldFilterCorrectly() {
        Page<Recipe> page = new PageImpl<>(List.of(recipe));
        when(recipeRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(recipeMapper.toResponse(any(Recipe.class))).thenReturn(recipeResponse);

        Page<RecipeResponse> result = recipeService.searchRecipes(
                "tarte",
                1L,
                DifficultyLevel.INTERMEDIATE,
                60,
                60,
                Pageable.unpaged()
        );

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void deleteRecipe_ShouldAuthorizeOwner() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(userRepository.findByEmail("chef@cuisenio.com")).thenReturn(Optional.of(user));

        recipeService.deleteRecipe(1L, "chef@cuisenio.com");

        verify(recipeRepository).delete(recipe);
    }

    @Test
    void deleteRecipe_ShouldPreventUnauthorized() {
        // Arrange
        User otherUser = new User();
        otherUser.setId(2L);
        otherUser.setEmail("other@mail.com");

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));
        when(userRepository.findByEmail("other@mail.com")).thenReturn(Optional.of(otherUser));

        // Act & Assert
        assertThrows(AccessDeniedException.class,
                () -> recipeService.deleteRecipe(1L, "other@mail.com"));
    }

    @Test
    void findById_ShouldThrowRecipeNotFoundException() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> recipeService.findById(999L));
    }

    @Test
    void getRecipesByUser_ShouldFindUserRecipes() {
        // Arrange
        Page<Recipe> userRecipes = new PageImpl<>(List.of(recipe));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(recipeRepository.findByUser(any(User.class), any(Pageable.class))).thenReturn(userRecipes);
        when(recipeMapper.toResponse(any(Recipe.class))).thenReturn(recipeResponse);

        // Act
        Page<RecipeResponse> result = recipeService.getRecipesByUser("chef@cuisenio.com", Pageable.unpaged());

        // Assert
        assertEquals(1, result.getTotalElements());
        verify(recipeRepository).findByUser(eq(user), any(Pageable.class));
    }
}