package com.youcode.cuisenio.features.mealplan.repository;

import com.youcode.cuisenio.features.mealplan.entity.MealPlanner;
import com.youcode.cuisenio.features.mealplan.entity.MealType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealPlannerRepository extends JpaRepository<MealPlanner, Long> {

    List<MealPlanner> findByUserIdAndPlanningDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    List<MealPlanner> findByUserIdAndDayOfWeekAndMealType(Long userId, DayOfWeek dayOfWeek, MealType mealType);

    @Query("SELECT mp FROM MealPlanner mp WHERE mp.user.id = :userId " +
            "AND mp.planningDate = :date " +
            "AND mp.mealType = :mealType")
    List<MealPlanner> findByUserIdAndDateAndMealType(Long userId, LocalDate date, MealType mealType);

    List<MealPlanner> findByUserIdOrderByPlanningDateAscDayOfWeekAscMealTypeAsc(Long userId);

    // statistiques sur les recettes les plus planifiées
    @Query("SELECT mp.recipe.id, COUNT(mp) FROM MealPlanner mp " +
            "WHERE mp.user.id = :userId " +
            "GROUP BY mp.recipe.id " +
            "ORDER BY COUNT(mp) DESC")
    List<Object[]> findMostUsedRecipesByUserId(Long userId);
}