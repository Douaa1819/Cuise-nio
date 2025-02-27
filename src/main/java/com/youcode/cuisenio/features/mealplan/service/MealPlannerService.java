package com.youcode.cuisenio.features.mealplan.service;

import com.youcode.cuisenio.features.auth.entity.User;
import com.youcode.cuisenio.features.auth.exception.UserNotFoundException;
import com.youcode.cuisenio.features.auth.repository.UserRepository;
import com.youcode.cuisenio.features.mealplan.dto.request.MealPlannerRequest;
import com.youcode.cuisenio.features.mealplan.dto.request.WeeklyPlanRequest;
import com.youcode.cuisenio.features.mealplan.dto.response.MealPlannerResponse;
import com.youcode.cuisenio.features.mealplan.entity.MealPlanner;
import com.youcode.cuisenio.features.mealplan.entity.MealType;
import com.youcode.cuisenio.features.mealplan.exception.MealPlannerNotFoundException;
import com.youcode.cuisenio.features.mealplan.mapper.MealPlannerMapper;
import com.youcode.cuisenio.features.mealplan.repository.MealPlannerRepository;
import com.youcode.cuisenio.features.recipe.entity.Recipe;
import com.youcode.cuisenio.features.recipe.exception.RecipeNotFoundException;
import com.youcode.cuisenio.features.recipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.youcode.cuisenio.features.mealplan.entity.DayOfWeek.MONDAY;

@Service
@Transactional
public class MealPlannerService {

    private final MealPlannerRepository mealPlannerRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final MealPlannerMapper mealPlannerMapper;

    public MealPlannerService(MealPlannerRepository mealPlannerRepository,
                              UserRepository userRepository,
                              RecipeRepository recipeRepository,
                              MealPlannerMapper mealPlannerMapper) {
        this.mealPlannerRepository = mealPlannerRepository;
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.mealPlannerMapper = mealPlannerMapper;
    }

    public MealPlannerResponse createMealPlan(Long userId, MealPlannerRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        Recipe recipe = recipeRepository.findById(request.recipeId())
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + request.recipeId()));

        MealPlanner mealPlanner = mealPlannerMapper.toEntity(request);
        mealPlanner.setUser(user);
        mealPlanner.setRecipe(recipe);

        MealPlanner savedMealPlanner = mealPlannerRepository.save(mealPlanner);
        return mealPlannerMapper.toResponse(savedMealPlanner);
    }

    public MealPlannerResponse getMealPlanById(Long id) {
        MealPlanner mealPlanner = mealPlannerRepository.findById(id)
                .orElseThrow(() -> new MealPlannerNotFoundException("Meal plan not found with id: " + id));
        return mealPlannerMapper.toResponse(mealPlanner);
    }


    public List<MealPlannerResponse> getAllMealPlansByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        List<MealPlanner> mealPlanners = mealPlannerRepository
                .findByUserIdOrderByPlanningDateAscDayOfWeekAscMealTypeAsc(userId);

        return mealPlanners.stream()
                .map(mealPlannerMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Obtenir le plan de repas pour une semaine spécifique
    public Map<String, List<MealPlannerResponse>> getWeeklyPlan(Long userId, WeeklyPlanRequest request) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        LocalDate startDate = request.startDate();
        LocalDate endDate = startDate.plusDays(6); // 7 jours incluant la date de début

        List<MealPlanner> weeklyPlan = mealPlannerRepository
                .findByUserIdAndPlanningDateBetween(userId, startDate, endDate);

        // Organiser par jour de la semaine
        Map<String, List<MealPlannerResponse>> planByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            String dayName = day.name();
            List<MealPlannerResponse> dayMeals = weeklyPlan.stream()
                    .filter(meal -> meal.getDayOfWeek() == MONDAY)
                    .map(mealPlannerMapper::toResponse)
                    .collect(Collectors.toList());

            planByDay.put(dayName, dayMeals);
        }

        return planByDay;
    }

    public MealPlannerResponse updateMealPlan(Long id, MealPlannerRequest request) {
        MealPlanner existingMealPlanner = mealPlannerRepository.findById(id)
                .orElseThrow(() -> new MealPlannerNotFoundException("Meal plan not found with id: " + id));

        if (!existingMealPlanner.getRecipe().getId().equals(request.recipeId())) {
            Recipe newRecipe = recipeRepository.findById(request.recipeId())
                    .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + request.recipeId()));
            existingMealPlanner.setRecipe(newRecipe);
        }

        mealPlannerMapper.updateEntityFromRequest(request, existingMealPlanner);

        MealPlanner updatedMealPlanner = mealPlannerRepository.save(existingMealPlanner);
        return mealPlannerMapper.toResponse(updatedMealPlanner);
    }

    // Supprimer un repas planifié
    public void deleteMealPlan(Long id) {
        if (!mealPlannerRepository.existsById(id)) {
            throw new MealPlannerNotFoundException("Meal plan not found with id: " + id);
        }
        mealPlannerRepository.deleteById(id);
    }

    // Obtenir les repas pour un jour et un type spécifique
    public List<MealPlannerResponse> getMealPlansByDayAndType(Long userId, DayOfWeek dayOfWeek, MealType mealType) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        List<MealPlanner> mealPlanners = mealPlannerRepository
                .findByUserIdAndDayOfWeekAndMealType(userId, dayOfWeek, mealType);

        return mealPlanners.stream()
                .map(mealPlannerMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Copier le plan d'une semaine à l'autre

    public Map<String, List<MealPlannerResponse>> copyWeeklyPlan(Long userId, LocalDate sourceStartDate, LocalDate targetStartDate) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        // Récupérer le plan de la semaine source
        LocalDate sourceEndDate = sourceStartDate.plusDays(6);
        List<MealPlanner> sourcePlan = mealPlannerRepository
                .findByUserIdAndPlanningDateBetween(userId, sourceStartDate, sourceEndDate);

        // Calculer la différence entre les dates pour l'ajustement

        long daysDifference = targetStartDate.toEpochDay() - sourceStartDate.toEpochDay();

        User user = userRepository.findById(userId).get();

        // Créer de nouveaux plans pour la semaine cible

        List<MealPlanner> targetPlan = sourcePlan.stream()
                .map(source -> {
                    MealPlanner target = new MealPlanner();
                    target.setUser(user);
                    target.setRecipe(source.getRecipe());

                    // Ajuster la date en conservant le même jour de la semaine

                    target.setPlanningDate(source.getPlanningDate().plusDays(daysDifference));
                    target.setDayOfWeek(source.getDayOfWeek());
                    target.setMealType(source.getMealType());
                    target.setServings(source.getServings());
                    target.setNotes(source.getNotes());
                    return target;
                })
                .collect(Collectors.toList());

        List<MealPlanner> savedTargetPlan = mealPlannerRepository.saveAll(targetPlan);

        // Organiser par jour de la semaine pour la réponse
        Map<String, List<MealPlannerResponse>> planByDay = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            String dayName = day.name();
            List<MealPlannerResponse> dayMeals = savedTargetPlan.stream()
                    .filter(meal -> meal.getDayOfWeek() == MONDAY)
                    .map(mealPlannerMapper::toResponse)
                    .collect(Collectors.toList());

            planByDay.put(dayName, dayMeals);
        }

        return planByDay;
    }

    // statistiques sur les recettes les plus utilisées dans les plans de repas
    public List<Map<String, Object>> getMostUsedRecipes(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        List<Object[]> results = mealPlannerRepository.findMostUsedRecipesByUserId(userId);

        return results.stream()
                .map(result -> {
                    Long recipeId = (Long) result[0];
                    Long usageCount = (Long) result[1];

                    Recipe recipe = recipeRepository.findById(recipeId)
                            .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + recipeId));

                    Map<String, Object> stat = new HashMap<>();
                    stat.put("recipeId", recipeId);
                    stat.put("recipeName", recipe.getTitle());
                    stat.put("usageCount", usageCount);
                    return stat;
                })
                .collect(Collectors.toList());
    }
}