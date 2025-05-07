package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "week_tbl")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Plan plan;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @OneToMany(mappedBy = "week")
    private Set<ScheduledMeal> scheduledMeals = Set.of();

    public Week() {

    }

    public Week(Plan plan, LocalDate startDate) {
        this.plan = plan;
        this.startDate = startDate;
    }

    public Week(Integer id, Plan plan, LocalDate startDate, Set<ScheduledMeal> scheduledMeals) {
        this.id = id;
        this.plan = plan;
        this.startDate = startDate;
        this.scheduledMeals = scheduledMeals;
    }

    public Integer getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Set<ScheduledMeal> getScheduledMeals() {
        return scheduledMeals;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setScheduledMeals(Set<ScheduledMeal> scheduledMeals) {
        this.scheduledMeals = scheduledMeals;
    }
}
