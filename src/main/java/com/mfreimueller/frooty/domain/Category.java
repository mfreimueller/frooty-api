package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "category_tbl")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, length = 32)
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Meal> meals = Set.of();

    public Category() {

    }

    public Category(String name, Set<Meal> meals) {
        this.name = name;
        this.meals = meals;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }
}
