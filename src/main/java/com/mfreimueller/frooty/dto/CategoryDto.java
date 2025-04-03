package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;

import java.util.List;

public class CategoryDto {
    private Integer id;
    private String name;
    private List<Integer> meals;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.meals = category.getMeals().stream().map(Meal::getId).toList();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getMeals() {
        return meals;
    }
}
