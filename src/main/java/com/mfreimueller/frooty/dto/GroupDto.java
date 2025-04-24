package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.User;

import java.util.HashSet;
import java.util.Set;

public class GroupDto {
    private Integer id;
    private String name;
    private Set<Integer> users;

    public GroupDto() {}

    public GroupDto(Integer id, String name, Set<Integer> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public GroupDto(Group group) {
        id = group.getId();
        name = group.getName();

        HashSet<Integer> userIds = new HashSet<>();
        group.getUsers().stream().map(User::getId).forEach(userIds::add);

        this.users = userIds;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Integer> getUsers() {
        return users;
    }
}
