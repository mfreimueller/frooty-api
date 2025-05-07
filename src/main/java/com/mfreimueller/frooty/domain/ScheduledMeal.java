package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "scheduled_meal_tbl")
public class ScheduledMeal {
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
    private Integer daySlot;

    @Column
    private Integer weekday;

    public ScheduledMeal() {

    }

    public ScheduledMeal(Week week, Meal meal, Integer daySlot, Integer weekday) {
        this.week = week;
        this.meal = meal;
        this.daySlot = daySlot;
        this.weekday = weekday;
    }

    public ScheduledMeal(Integer id, Week week, Meal meal, Integer daySlot, Integer weekday) {
        this.id = id;
        this.week = week;
        this.meal = meal;
        this.daySlot = daySlot;
        this.weekday = weekday;
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

    public Integer getDaySlot() {
        return daySlot;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public void setDaySlot(Integer daySlot) {
        this.daySlot = daySlot;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }
}
