package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 32)
    private String username;

    @Column(unique = true, length = 64)
    @Email
    private String email;

    @Column(length = 60)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_plan_tbl",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "plan_id")
    )
    private Set<Plan> plans = Set.of();

    public User() {

    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(Integer id, String username, String password, Set<Plan> plans) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.plans = plans;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Plan> getPlans() {
        return plans;
    }

    public void setPlans(Set<Plan> plans) {
        this.plans = plans;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return Objects.equals(((User) obj).getId(), id);
        } else {
            return super.equals(obj);
        }
    }
}
