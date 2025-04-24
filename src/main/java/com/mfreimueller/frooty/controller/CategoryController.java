package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.CategoryDto;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll()
                .map(CategoryDto::new)
                .toList();
    }

    @PostMapping
    public CategoryDto createOne(@RequestBody CategoryDto category) {
        return new CategoryDto(categoryService.createOne(category));
    }

    @GetMapping("/{id}")
    public CategoryDto findOne(@PathVariable Integer id) {
        return new CategoryDto(categoryService.findOne(id));
    }

    @GetMapping("/{id}/meals")
    public List<MealDto> findMealsOfOne(@PathVariable Integer id) {
        return categoryService.findMealsOfOne(id)
                .stream()
                .map(MealDto::new)
                .toList();
    }

    @PutMapping("/{id}")
    public CategoryDto updateOne(@PathVariable Integer id, @RequestBody CategoryDto category) {
        return new CategoryDto(categoryService.updateOne(id, category));
    }
}
