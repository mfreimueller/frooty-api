package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "meal_tbl")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, length = 64)
    private String name;
    @Column
    private Integer complexity;

    @ManyToMany
    @JoinTable(
            name = "meal_category_tbl",
            joinColumns = @JoinColumn(name = "meal_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    public Meal() {

    }

    public Meal(String name, Integer complexity, Set<Category> categories) {
        this.name = name;
        this.complexity = complexity;
        this.categories = categories;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getComplexity() {
        return complexity;
    }

    public void setComplexity(Integer complexity) {
        this.complexity = complexity;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
