package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.dto.GroupDto;
import com.mfreimueller.frooty.dto.UserDto;
import com.mfreimueller.frooty.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping
    public List<GroupDto> findAll() {
        return groupService.findAll()
                .stream()
                .map(GroupDto::new)
                .toList();
    }

    @PostMapping
    public GroupDto createOne(@RequestBody Group group) {
        return new GroupDto(groupService.createOne(group));
    }

    @GetMapping("/{id}")
    public GroupDto findOne(@PathVariable Integer id) {
        return new GroupDto(groupService.findOne(id));
    }

    @GetMapping("/{id}/users")
    public List<UserDto> findUsersOfOne(@PathVariable Integer id) {
        return groupService.findUsersOfOne(id)
                .stream()
                .map(UserDto::new)
                .toList();
    }

    @PutMapping("/{id}")
    public GroupDto updateOne(@PathVariable Integer id, @RequestBody Group group) {
        return new GroupDto(groupService.updateOne(id, group));
    }
}
