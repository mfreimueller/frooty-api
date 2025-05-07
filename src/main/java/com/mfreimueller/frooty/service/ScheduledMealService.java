package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.*;
import com.mfreimueller.frooty.dto.CreateScheduledMealDto;
import com.mfreimueller.frooty.dto.ScheduledMealDto;
import com.mfreimueller.frooty.dto.UpdateScheduledMealDto;
import com.mfreimueller.frooty.repositories.ScheduledMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ScheduledMealService {
    @Autowired
    private ScheduledMealRepository scheduledMealRepository;
    @Autowired
    private MealService mealService;
    @Autowired
    private WeekService weekService;
    @Autowired
    private ConversionService conversionService;

    public ScheduledMealDto createNewEntry(Integer planId, Integer weekId, CreateScheduledMealDto createScheduledMealDto) {
        final Week week = weekService.getWeekById(planId, weekId);
        final Meal meal = mealService.findOne(createScheduledMealDto.mealId());

        final ScheduledMeal entry = new ScheduledMeal(week, meal, createScheduledMealDto.daySlot(), createScheduledMealDto.weekday());
        return conversionService.convert(scheduledMealRepository.save(entry), ScheduledMealDto.class);
    }

    public ScheduledMealDto updateScheduledMeal(Integer scheduledMealId, Integer planId, UpdateScheduledMealDto updateScheduledMealDto) {
        ScheduledMeal scheduledMeal = scheduledMealRepository.findById(scheduledMealId).orElseThrow();

        // make sure that the scheduled meal is really part of the plan we previously
        // made sure the user is a part of.
        if (!Objects.equals(scheduledMeal.getWeek().getPlan().getId(), planId)) {
            throw new IllegalArgumentException("The plan id is not valid!");
        }

        scheduledMeal.setWeekday(updateScheduledMealDto.weekday());
        scheduledMeal.setDaySlot(updateScheduledMealDto.daySlot());

        final Meal meal = mealService.findOne(updateScheduledMealDto.mealId());

        if (meal == null) {
            throw new IllegalArgumentException("The meal identified by id " + updateScheduledMealDto.mealId() + " does not exist!");
        }

        scheduledMeal.setMeal(meal);
        return conversionService.convert(scheduledMealRepository.save(scheduledMeal), ScheduledMealDto.class);
    }

    public void deleteMealFromWeek(Integer scheduledMealId, Integer planId) {
        ScheduledMeal scheduledMeal = scheduledMealRepository.findById(scheduledMealId).orElseThrow();

        // make sure that the scheduled meal is really part of the plan we previously
        // made sure the user is a part of.
        if (!Objects.equals(scheduledMeal.getWeek().getPlan().getId(), planId)) {
            throw new IllegalArgumentException("The plan id is not valid!");
        }

        scheduledMealRepository.delete(scheduledMeal);
    }
}
