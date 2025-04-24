package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;

public class MealDto {
    private Integer id;
    private String name;
    private Integer complexity;
    private Integer categoryId;

    public MealDto() {}

    public MealDto(Integer id, String name, Integer complexity, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.complexity = complexity;
        this.categoryId = categoryId;
    }

    public MealDto(Meal meal) {
        id = meal.getId();
        name = meal.getName();
        complexity = meal.getComplexity();
        categoryId = meal.getCategory().map(Category::getId).orElse(null);
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

    public Integer getCategoryId() {
        return categoryId;
    }
}
