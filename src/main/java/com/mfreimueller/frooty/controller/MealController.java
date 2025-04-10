package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealRepository mealRepository;

    @GetMapping
    public List<MealDto> getAll() {
        return StreamSupport.stream(mealRepository.findAll().spliterator(), false)
                .map(MealDto::new)
                .toList();
    }

    @PostMapping
    public MealDto createOne(@RequestBody Meal meal) {
        return new MealDto(mealRepository.save(meal));
    }

    @GetMapping("/{id}")
    public MealDto findOne(@PathVariable Integer id) {
        return mealRepository.findById(id)
                .map(MealDto::new)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @PutMapping("/{id}")
    public MealDto updateOne(@PathVariable Integer id, @RequestBody Meal meal) {
        return mealRepository.findById(id)
                .map(dbMeal -> {
                    dbMeal.setName(meal.getName());
                    dbMeal.setComplexity(meal.getComplexity());
                    return mealRepository.save(dbMeal);
                })
                .map(MealDto::new)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
}
