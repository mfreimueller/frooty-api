package com.mfreimueller.frooty.domain;

import jakarta.persistence.*;

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
    @Column(length = 60)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_group_tbl",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups = Set.of();

    public User() {

    }

    public User(String username, String password, Set<Group> groups) {
        this.username = username;
        this.password = password;
        this.groups = groups;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
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
