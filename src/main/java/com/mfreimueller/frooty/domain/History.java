package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private Integer rating;

    public History() {

    }

    public History(Group group, Meal meal, Date date, Integer rating) {
        this.group = group;
        this.meal = meal;
        this.date = date;
        this.rating = rating;
    }

    public History(Integer id, Group group, Meal meal, Date date, Integer rating) {
        this.id = id;
        this.group = group;
        this.meal = meal;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
