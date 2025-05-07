package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.CreateUpdateMealDto;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping
    public List<MealDto> findAll(Pageable pageable) {
        return mealService.getAllMeals(pageable);
    }

    @PostMapping
    public MealDto createNewMeal(@RequestBody CreateUpdateMealDto createUpdateMealDto) {
        return mealService.createNewMeal(createUpdateMealDto);
    }

    @PutMapping("/{mealId}")
    public MealDto updateMeal(@PathVariable Integer mealId, @RequestBody CreateUpdateMealDto createUpdateMealDto) {
        return mealService.updateMeal(mealId, createUpdateMealDto);
    }
}
