package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.annotations.CheckPlanAccess;
import com.mfreimueller.frooty.dto.CreateScheduledMealDto;
import com.mfreimueller.frooty.dto.ScheduledMealDto;
import com.mfreimueller.frooty.dto.UpdateScheduledMealDto;
import com.mfreimueller.frooty.dto.WeekDto;
import com.mfreimueller.frooty.service.ScheduledMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plan/{planId}/week/{weekId}/meal")
public class ScheduledMealController {
    @Autowired
    private ScheduledMealService scheduledMealService;

    @PostMapping
    @CheckPlanAccess(planIdParam = "planId")
    public ScheduledMealDto addMealToCurrentWeek(@PathVariable Integer planId, @PathVariable Integer weekId, @RequestBody CreateScheduledMealDto createScheduledMealDto) {
        return scheduledMealService.createNewEntry(planId, weekId, createScheduledMealDto);
    }

    @PutMapping("/{mealId}")
    @CheckPlanAccess(planIdParam = "planId")
    public ScheduledMealDto updateMealOfPlanAndWeek(@PathVariable Integer planId, @PathVariable Integer weekId, @PathVariable Integer mealId, @RequestBody UpdateScheduledMealDto updateScheduledMealDto) {
        return scheduledMealService.updateScheduledMeal(mealId, planId, updateScheduledMealDto);
    }

    @DeleteMapping("/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckPlanAccess(planIdParam = "planId")
    public void deleteMealFromCurrentWeek(@PathVariable Integer planId, @PathVariable Integer weekId, @PathVariable Integer mealId) {
        scheduledMealService.deleteMealFromWeek(mealId, planId);
    }
}
