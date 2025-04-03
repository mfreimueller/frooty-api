package com.mfreimueller.frooty.domain;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "group_tbl")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 64)
    private String name;

    @ManyToMany(mappedBy = "groups")
    private Set<User> users = Set.of();

    public Group() {

    }

    public Group(String name, Set<User> users) {
        this.name = name;
        this.users = users;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
