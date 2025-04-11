package com.mfreimueller.frooty.payload.response;

public class PredictionResponse {
    private Integer[][] meals;

    public PredictionResponse(Integer[][] meals) {
        this.meals = meals;
    }

    public Integer[][] getMeals() {
        return meals;
    }
}
