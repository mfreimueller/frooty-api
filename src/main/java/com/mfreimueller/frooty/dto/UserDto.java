package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.User;

public class UserDto {
    private final Integer id;
    private final String username;

    public UserDto(User user) {
        id = user.getId();
        username = user.getUsername();
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
