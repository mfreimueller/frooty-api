package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "history_entry_tbl")
public class HistoryEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "week_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Week week;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Meal meal;

    @Column
    private Integer order;

    @Column
    private Integer day;

    public HistoryEntry() {

    }

    public HistoryEntry(Week week, Meal meal, Integer order, Integer day) {
        this.week = week;
        this.meal = meal;
        this.order = order;
        this.day = day;
    }

    public HistoryEntry(Integer id, Week week, Meal meal, Integer order, Integer day) {
        this.id = id;
        this.week = week;
        this.meal = meal;
        this.order = order;
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public Week getWeek() {
        return week;
    }

    public Meal getMeal() {
        return meal;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getDay() {
        return day;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
