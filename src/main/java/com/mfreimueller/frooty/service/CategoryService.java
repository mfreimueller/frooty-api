package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false)
                .toList();
    }

    public Category createOne(Category category) {
        return categoryRepository.save(category);
    }

    public Category findOne(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public List<Meal> findMealsOfOne(Integer id) {
        return categoryRepository.findById(id)
                .map(Category::getMeals)
                .map(s -> s.stream().sorted((m1, m2) -> m1.getId().compareTo(m2.getId())).toList())
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Category updateOne(Integer id, Category category) {
        return categoryRepository.findById(id)
                .map(dbCategory -> {
                    dbCategory.setName(category.getName());
                    return categoryRepository.save(dbCategory);
                })
                .orElseThrow(() -> new EntityNotFoundException(id, "category"));
    }
}
