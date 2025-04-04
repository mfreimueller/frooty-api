package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.GroupDto;
import com.mfreimueller.frooty.dto.UserDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.GroupRepository;
import com.mfreimueller.frooty.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<GroupDto> getAll(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .map(User::getGroups)
                .map(s ->
                        s.stream()
                                .map(GroupDto::new)
                                .sorted((g1, g2) -> g1.getId().compareTo(g2.getId()))
                                .toList()
                )
                .orElseThrow();
    }

    @PostMapping
    public GroupDto createOne(Principal principal, @RequestBody Group group) {
        Group newGroup = groupRepository.save(group);

        final User currentUser = user(principal);

        HashSet<Group> groups = new HashSet<>(currentUser.getGroups());
        groups.add(newGroup);
        currentUser.setGroups(groups);

        userRepository.save(currentUser);

        newGroup.setUsers(Set.of(currentUser));

        return new GroupDto(newGroup);
    }

    @GetMapping("/{id}")
    public GroupDto findOne(Principal principal, @PathVariable Integer id) {
        return groupRepository.findById(id)
                .filter(group -> group.getUsers().contains(user(principal)))
                .map(GroupDto::new)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @GetMapping("/{id}/users")
    public List<UserDto> findUsersOfOne(Principal principal, @PathVariable Integer id) {
        return groupRepository.findById(id)
                .filter(group -> group.getUsers().contains(user(principal)))
                .map(Group::getUsers)
                .map(s -> s.stream().map(UserDto::new).toList())
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    @PutMapping("/{id}")
    public GroupDto updateOne(Principal principal, @PathVariable Integer id, @RequestBody Group group) {
        return groupRepository.findById(id)
                .filter(g -> g.getUsers().contains(user(principal)))
                .map(g -> {
                    g.setName(group.getName());

                    // TODO: make sure that we don't wipe all users. - invite mechanic?
                    g.setUsers(group.getUsers());
                    return new GroupDto(groupRepository.save(g));
                })
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    private User user(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElseThrow();
    }
}
