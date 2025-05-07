package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.annotations.CheckPlanAccess;
import com.mfreimueller.frooty.dto.UpdateUserDto;
import com.mfreimueller.frooty.dto.UserDto;
import com.mfreimueller.frooty.dto.WeekDto;
import com.mfreimueller.frooty.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public UserDto getCurrentUser() {
        return userService.getCurrentUserDto();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCurrentUser(@RequestBody UpdateUserDto updateUserDto) {
        userService.updateUser(updateUserDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser() {
        userService.deleteCurrentUser();
    }
}
