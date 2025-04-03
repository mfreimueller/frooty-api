package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.Meal;

public class LightMealDto {
    private final Integer id;
    private final String name;

    public LightMealDto(Meal meal) {
        id = meal.getId();
        name = meal.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
