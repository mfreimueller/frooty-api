package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.domain.Plan;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlanMealService {
    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private MealService mealService;

    @Autowired
    private ConversionService conversionService;

    public List<MealDto> getAllMealsForPlan(Integer planId) {
        Assert.notNull(planId, "planId must not be null.");

        return planRepository.findById(planId)
                .stream()
                .map(Plan::getSelectedMeals)
                .map(m -> conversionService.convert(m, MealDto.class))
                .toList();
    }

    public void addMealToPlan(Integer planId, Integer mealId) {
        Assert.notNull(planId, "planId must not be null.");
        Assert.notNull(mealId, "mealId must not be null.");

        Plan plan = planRepository.findById(planId).orElseThrow();
        Meal meal = mealService.findOne(planId);

        Set<Meal> selectedMeals = plan.getSelectedMeals();
        selectedMeals.add(meal);
        plan.setSelectedMeals(selectedMeals);

        planRepository.save(plan);
    }

    public void removeMealFromPlan(Integer planId, Integer mealId) {
        Assert.notNull(planId, "planId must not be null.");
        Assert.notNull(mealId, "mealId must not be null.");

        Plan plan = planRepository.findById(planId).orElseThrow();
        Set<Meal> meals = plan.getSelectedMeals();
        meals = meals.stream()
                .filter(m -> !Objects.equals(m.getId(), mealId))
                .collect(Collectors.toSet());

        plan.setSelectedMeals(meals);
        planRepository.save(plan);
    }
}
