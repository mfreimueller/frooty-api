package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "meal_ingredient_tbl")
public class MealIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Meal meal;

    @ManyToOne
    private Ingredient ingredient;

    @Column
    private String unit;

    @Column
    private String amount;

    public MealIngredient() {}

    public MealIngredient(Integer id, Meal meal, Ingredient ingredient, String unit, String amount) {
        this.id = id;
        this.meal = meal;
        this.ingredient = ingredient;
        this.unit = unit;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
