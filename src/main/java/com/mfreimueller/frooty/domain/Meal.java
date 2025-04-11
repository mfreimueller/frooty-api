package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Optional;

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

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;

    public Meal() {

    }

    public Meal(String name, Integer complexity, Category category) {
        this.name = name;
        this.complexity = complexity;
        this.category = category;
    }

    public Meal(Integer id, String name, Integer complexity, Category category) {
        this.id = id;
        this.name = name;
        this.complexity = complexity;
        this.category = category;
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

    public Optional<Category> getCategory() {
        return Optional.ofNullable(category);
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
