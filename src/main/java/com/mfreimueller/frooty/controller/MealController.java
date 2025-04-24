package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping
    public List<MealDto> findAll() {
        return mealService.findAll()
                .map(MealDto::new)
                .toList();
    }

    @PostMapping
    public MealDto createOne(@RequestBody MealDto meal) {
        return new MealDto(mealService.createOne(meal));
    }

    @GetMapping("/{id}")
    public MealDto findOne(@PathVariable Integer id) {
        return new MealDto(mealService.findOne(id));
    }

    @PutMapping("/{id}")
    public MealDto updateOne(@PathVariable Integer id, @RequestBody MealDto meal) {
        return new MealDto(mealService.updateOne(id, meal));
    }
}
