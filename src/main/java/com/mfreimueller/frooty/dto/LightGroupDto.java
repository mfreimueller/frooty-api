package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.User;

import java.util.List;

public class LightGroupDto {
    private final Integer id;
    private final String name;

    public LightGroupDto(Group group) {
        id = group.getId();
        name = group.getName();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
