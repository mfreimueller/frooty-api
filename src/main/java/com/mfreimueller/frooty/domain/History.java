package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "history_tbl")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Meal meal;

    @Column(name = "scheduled_on")
    @Temporal(TemporalType.DATE)
    private LocalDate scheduledOn;

    @Column
    private Integer rating;

    public History() {

    }

    public History(Group group, Meal meal, LocalDate scheduledOn, Integer rating) {
        this.group = group;
        this.meal = meal;
        this.scheduledOn = scheduledOn;
        this.rating = rating;
    }

    public History(Integer id, Group group, Meal meal, LocalDate scheduledOn, Integer rating) {
        this.id = id;
        this.group = group;
        this.meal = meal;
        this.scheduledOn = scheduledOn;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public LocalDate getScheduledOn() {
        return scheduledOn;
    }

    public void setScheduledOn(LocalDate scheduledOn) {
        this.scheduledOn = scheduledOn;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
