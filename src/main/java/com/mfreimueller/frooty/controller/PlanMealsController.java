package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.annotations.CheckPlanAccess;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.service.PlanMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan/{planId}/meal")
public class PlanMealsController {
    @Autowired
    private PlanMealService planMealService;

    @GetMapping
    @CheckPlanAccess(planIdParam = "planId")
    public List<MealDto> getAllMealsOfPlan(@PathVariable Integer planId) {
        return planMealService.getAllMealsForPlan(planId);
    }

    @PostMapping("/{mealId}")
    @CheckPlanAccess(planIdParam = "planId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addMealToPlan(@PathVariable Integer planId, @PathVariable Integer mealId) {
        planMealService.addMealToPlan(planId, mealId);
    }

    @DeleteMapping("/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckPlanAccess(planIdParam = "planId")
    public void removeMealFromPlan(@PathVariable Integer planId, @PathVariable Integer mealId) {
        planMealService.removeMealFromPlan(planId, mealId);
    }
}
