package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealRepository mealRepository;

    @GetMapping
    public Iterable<Meal> getAll() {
        return mealRepository.findAll();
    }

    @PostMapping
    public Meal createOne(@RequestBody Meal meal) {
        return mealRepository.save(meal);
    }

    @GetMapping("/{id}")
    public Meal findOne(@PathVariable Integer id) {
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Meal updateOne(@PathVariable Integer id, @RequestBody Meal meal) {
        return mealRepository.findById(id)
                .map(dbMeal -> {
                    dbMeal.setName(meal.getName());
                    dbMeal.setComplexity(meal.getComplexity());
                    return mealRepository.save(dbMeal);
                })
                .orElseGet(() -> mealRepository.save(meal));
    }
}
