package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.CategoryDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Stream<Category> findAll() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(), false);
    }

    public Category createOne(CategoryDto categoryDto) {
        Assert.notNull(categoryDto.getId(), "category must not be null.");
        Assert.isNull(categoryDto.getId(), "category id must be null.");

        final Category category = new Category(categoryDto.getName(), Set.of());

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

    public Category updateOne(Integer id, CategoryDto category) {
        Assert.notNull(id, "id must not be null.");
        Assert.notNull(category, "category must not be null.");
        Assert.notNull(category.getName(), "category name must not be null.");
        Assert.isTrue(!category.getName().isEmpty(), "category name must not be empty.");

        return categoryRepository.findById(id)
                .map(dbCategory -> {
                    dbCategory.setName(category.getName());
                    return categoryRepository.save(dbCategory);
                })
                .orElseThrow(() -> new EntityNotFoundException(id, "category"));
    }
}
