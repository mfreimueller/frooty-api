package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public Iterable<Category> getAll() {
        return categoryRepository.findAll();
    }

    @PostMapping
    public Category createOne(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping("/{id}")
    public Category findOne(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @GetMapping("/{id}/meals")
    public Set<Meal> findMealsOfOne(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .map(Category::getMeals)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @PutMapping("/{id}")
    public Category updateOne(@PathVariable Integer id, @RequestBody Category category) {
        return categoryRepository.findById(id)
                .map(dbCategory -> {
                    dbCategory.setName(category.getName());
                    return categoryRepository.save(dbCategory);
                })
                .orElseGet(() -> categoryRepository.save(category));
    }
}
