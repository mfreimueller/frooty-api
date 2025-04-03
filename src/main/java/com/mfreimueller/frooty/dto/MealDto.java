package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;

import java.util.List;

public class MealDto {
    private final Integer id;
    private final String name;
    private final Integer complexity;

    private final List<Integer> categories;

    public MealDto(Meal meal) {
        id = meal.getId();
        name = meal.getName();
        complexity = meal.getComplexity();
        categories = meal.getCategories().stream().map(Category::getId).toList();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public List<Integer> getCategories() {
        return categories;
    }
}
