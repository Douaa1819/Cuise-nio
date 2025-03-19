package com.youcode.cuisenio.features.recipe.service.impl;

import com.youcode.cuisenio.features.auth.entity.User;
import com.youcode.cuisenio.features.auth.repository.UserRepository;
import com.youcode.cuisenio.features.auth.service.UserService;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.AddRecipeImage;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeDetailsRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.request.RecipeRequest;
import com.youcode.cuisenio.features.recipe.dto.recipe.response.RecipeResponse;
import com.youcode.cuisenio.features.recipe.entity.*;
import com.youcode.cuisenio.features.recipe.exception.CategoryNotFoundException;
import com.youcode.cuisenio.features.recipe.exception.IngredientNotFoundException;
import com.youcode.cuisenio.features.recipe.exception.RecipeNotFoundException;
import com.youcode.cuisenio.features.recipe.mapper.RecipeMapper;
import com.youcode.cuisenio.features.recipe.repository.*;
import com.youcode.cuisenio.features.recipe.service.RecipeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@Slf4j
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final RecipeMapper recipeMapper;
    private final FileStorageService fileStorageService;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final CategoryRepository categoryRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             IngredientRepository ingredientRepository,
                             RecipeMapper recipeMapper,
                             UserRepository userRepository,
                             FileStorageService fileStorageService,
                             RecipeIngredientRepository recipeIngredientRepository,
                             RecipeStepRepository recipeStepRepository,
                             CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
        this.recipeMapper = recipeMapper;
        this.fileStorageService=fileStorageService;
        this.recipeIngredientRepository=recipeIngredientRepository;
        this.recipeStepRepository=recipeStepRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public RecipeResponse createRecipe(String email, RecipeRequest request) {
        log.warn("Received RecipeRequest: " + request);


        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        Recipe recipe = new Recipe();
        recipe.setTitle(request.title());
        recipe.setDescription(request.description());
        recipe.setCookingTime(request.cookingTime());
        recipe.setDifficultyLevel(request.difficultyLevel());
        recipe.setPreparationTime(request.preparationTime());
        recipe.setServings(request.servings());
        recipe.setUser(user);
        Category category = categoryRepository.findById(request.categoryIds()).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        recipe.setCategorie(category);
        Recipe savedRecipe = recipeRepository.save(recipe);

        log.warn("Received ingredients: " + request.ingredients());
        List<RecipeIngredient> recipeIngredients = request.ingredients().stream()
                .map(ingredientRequest -> {
                        Ingredient ingredient = ingredientRepository.findById(ingredientRequest.ingredientId())
                            .orElseThrow(() -> new IngredientNotFoundException(
                                    "Ingredient not found with id: " + ingredientRequest.ingredientId()));

                    RecipeIngredient recipeIngredient = new RecipeIngredient();
                    recipeIngredient.setRecipe(savedRecipe);

                    recipeIngredient.setIngredient(ingredient);
                    recipeIngredient.setQuantity(ingredientRequest.quantity());
                    recipeIngredient.setUnit(ingredientRequest.unit());
                    return recipeIngredient;
                })
                .collect(Collectors.toList());

        recipeIngredientRepository.saveAll(recipeIngredients);

        Recipe finalRecipe1 = recipe;
        List<RecipeStep> recipeSteps = request.steps().stream()
                .map(stepRequest -> {
                    RecipeStep recipeStep = new RecipeStep();
                    recipeStep.setStepNumber(stepRequest.stepNumber());
                    recipeStep.setDescription(stepRequest.description());
                    recipeStep.setRecipe(finalRecipe1);
                    return recipeStep;
                })
                .collect(Collectors.toList());
        recipeStepRepository.saveAll(recipeSteps);


        recipe.setSteps(recipeSteps);
        recipe.setRecipeIngredients(recipeIngredients);

        return recipeMapper.toResponse(recipe);
    }

    @Override
    public RecipeResponse addImage(Long id, AddRecipeImage recipeImage) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));
        recipe.setImageUrl(uploadFile(recipeImage.imageUrl()));
        return recipeMapper.toResponse(recipe);
    }

    @Override
    public Page<RecipeResponse> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable).map(recipeMapper::toResponse);
    }

    public RecipeResponse findById(Long id) {
        return recipeRepository.findById(id).map(recipeMapper::toResponse)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe non trouvé avec l'id: " + id));
    }

    public RecipeResponse create(RecipeRequest recipeRequest) {
        Recipe recipe = recipeMapper.toEntity(recipeRequest);
        recipe = recipeRepository.save(recipe);
        return recipeMapper.toResponse(recipe);
    }

    public RecipeResponse update(Long id, RecipeRequest recipeRequest) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow(()-> new RecipeNotFoundException("Recipe Not Found with id : "+id));
        return recipeMapper.toResponse(recipeRepository.save(recipe));
    }

    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException("Recipe Not Found with id : "+id);
        }
        recipeRepository.deleteById(id);
    }


    private String uploadFile(MultipartFile file) {
        return fileStorageService.storeFile(file);
    }


    @Override
    public Page<RecipeResponse> getRecipesByUser(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return recipeRepository.findByUser(user, pageable).map(recipeMapper::toResponse);
    }

    @Override
    public RecipeResponse updateRecipe(Long recipeId, String email, RecipeRequest request) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!recipe.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not the owner of this recipe");
        }

        recipe.setTitle(request.title());
        recipe.setDescription(request.description());
        recipe.setPreparationTime(request.preparationTime());
        recipe.setCookingTime(request.cookingTime());
        recipe.setServings(request.servings());
        recipe.setDifficultyLevel(request.difficultyLevel());

        recipeIngredientRepository.deleteByRecipe(recipe);
        List<RecipeIngredient> newIngredients = request.ingredients().stream()
                .map(ingredientRequest -> {
                    Ingredient ingredient = ingredientRepository.findById(ingredientRequest.ingredientId())
                            .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found"));
                    RecipeIngredient recipeIngredient = new RecipeIngredient();
                    recipeIngredient.setRecipe(recipe);
                    recipeIngredient.setIngredient(ingredient);
                    recipeIngredient.setQuantity(ingredientRequest.quantity());
                    recipeIngredient.setUnit(ingredientRequest.unit());
                    return recipeIngredient;
                })
                .collect(Collectors.toList());
        recipe.setRecipeIngredients(newIngredients);

        recipeStepRepository.deleteByRecipe(recipe);
        List<RecipeStep> newSteps = request.steps().stream()
                .map(stepRequest -> {
                    RecipeStep recipeStep = new RecipeStep();
                    recipeStep.setRecipe(recipe);
                    recipeStep.setStepNumber(stepRequest.stepNumber());
                    recipeStep.setDescription(stepRequest.description());
                    return recipeStep;
                })
                .collect(Collectors.toList());
        recipe.setSteps(newSteps);

        return recipeMapper.toResponse(recipeRepository.save(recipe));
    }

    @Override
    public void deleteRecipe(Long recipeId, String email) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!recipe.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not the owner of this recipe");
        }

        recipeRepository.delete(recipe);
    }

    @Override
    public Page<RecipeResponse> searchRecipes(
            String query,
            Long categoryId,
            DifficultyLevel difficulty,
            Integer maxPrepTime,
            Integer maxCookTime,
            Pageable pageable
    ) {
        Specification<Recipe> spec = Specification.where(null);

        if (query != null && !query.isEmpty()) {
            String searchQuery = "%" + query.toLowerCase() + "%";
            spec = spec.and((root, criteriaQuery, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("title")), searchQuery),
                            cb.like(cb.lower(root.get("description")), searchQuery)
                    )
            );
        }


        if (categoryId != null) {
            spec = spec.and((root, criteriaQuery, cb) ->
                    cb.equal(root.get("categorie").get("id"), categoryId)
            );
        }

        if (difficulty != null) {
            spec = spec.and((root, criteriaQuery, cb) ->
                    cb.equal(root.get("difficultyLevel"), difficulty)
            );
        }

        if (maxPrepTime != null) {
            spec = spec.and((root, criteriaQuery, cb) ->
                    cb.lessThanOrEqualTo(root.get("preparationTime"), maxPrepTime)
            );
        }

        if (maxCookTime != null) {
            spec = spec.and((root, criteriaQuery, cb) ->
                    cb.lessThanOrEqualTo(root.get("cookingTime"), maxCookTime)
            );
        }

        return recipeRepository.findAll(spec, pageable)
                .map(recipeMapper::toResponse);
    }
}