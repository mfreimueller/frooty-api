package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.User;

import java.util.List;

public class GroupDto {
    private final Integer id;
    private final String name;
    private final List<Integer> users;

    public GroupDto(Group group) {
        id = group.getId();
        name = group.getName();
        users = group.getUsers().stream().map(User::getId).toList();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getUsers() {
        return users;
    }
}
