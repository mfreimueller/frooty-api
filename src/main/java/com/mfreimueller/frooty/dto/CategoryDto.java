package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;

import java.util.HashSet;
import java.util.Set;

public class CategoryDto {
    private Integer id;
    private String name;
    private Set<Integer> meals;

    public CategoryDto(){
    }

    public CategoryDto(Integer id, String name, Set<Integer> meals) {
        this.id = id;
        this.name = name;
        this.meals = meals;
    }

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();

        HashSet<Integer> mealIds = new HashSet<>();
        category.getMeals().stream().map(Meal::getId).forEach(mealIds::add);

        this.meals = mealIds;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getMeals() {
        return meals;
    }
}
