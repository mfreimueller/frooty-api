package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.dto.CategoryDto;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryDto> getAll() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .map(CategoryDto::new)
                .toList();
    }

    @PostMapping
    public CategoryDto createOne(@RequestBody Category category) {
        return new CategoryDto(categoryRepository.save(category));
    }

    @GetMapping("/{id}")
    public CategoryDto findOne(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .map(CategoryDto::new)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @GetMapping("/{id}/meals")
    public List<MealDto> findMealsOfOne(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .map(Category::getMeals)
                .map(s -> s.stream().map(MealDto::new).sorted((m1, m2) -> m1.getId().compareTo(m2.getId())).toList())
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @PutMapping("/{id}")
    public CategoryDto updateOne(@PathVariable Integer id, @RequestBody Category category) {
        return categoryRepository.findById(id)
                .map(dbCategory -> {
                    dbCategory.setName(category.getName());
                    return new CategoryDto(categoryRepository.save(dbCategory));
                })
                .orElseThrow(() -> new EntityNotFoundException(id, "category"));
    }
}
