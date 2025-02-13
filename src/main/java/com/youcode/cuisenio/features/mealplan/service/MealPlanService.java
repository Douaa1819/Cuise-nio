package com.youcode.cuisenio.features.mealplan.service;

import com.youcode.cuisenio.features.mealplan.entity.MealPlan;
import com.youcode.cuisenio.features.mealplan.repository.MealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealPlanService {
    @Autowired
    private MealPlanRepository mealPlanRepository;

    public List<MealPlan> findByUserId(Long userId) {
        return mealPlanRepository.findByUserId(userId);
    }

    public MealPlan save(MealPlan mealPlan) {
        return mealPlanRepository.save(mealPlan);
    }
}