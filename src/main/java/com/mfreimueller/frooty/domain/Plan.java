package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "group_tbl")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 64)
    private String name;

    @ManyToOne
    private User owner;

    @ManyToMany(mappedBy = "plans")
    private Set<User> users = Set.of();

    @OneToMany(mappedBy = "plan")
    private Set<Week> weeks = Set.of();

    @ManyToMany
    @JoinTable(
            name = "plan_meal_tbl",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private Set<Meal> selectedMeals = Set.of();

    public Plan() {

    }

    public Plan(String name, User owner) {
        this.name = name;
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

    public User getOwner() {
        return owner;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(Set<Week> weeks) {
        this.weeks = weeks;
    }

    public Set<Meal> getSelectedMeals() {
        return selectedMeals;
    }

    public void setSelectedMeals(Set<Meal> selectedMeals) {
        this.selectedMeals = selectedMeals;
    }
}
