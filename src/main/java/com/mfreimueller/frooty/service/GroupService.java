package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.GroupRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CurrentUserService currentUserService;
    @Autowired
    private UserService userService;

    public List<Group> findAll() {
        final User currentUser = currentUserService.getCurrentUser();

        return currentUser.getGroups()
                .stream()
                .sorted((g1, g2) -> g1.getId().compareTo(g2.getId()))
                .toList();
    }

    public Group createOne(Group group) {
        Assert.isNull(group.getId(), "The id of the group to create must be null.");

        final User currentUser = currentUserService.getCurrentUser();

        Group newGroup = groupRepository.save(group);

        HashSet<Group> groups = new HashSet<>(currentUser.getGroups());
        groups.add(newGroup);
        currentUser.setGroups(groups);

        userService.updateUser(currentUser);

        newGroup.setUsers(Set.of(currentUser));

        return newGroup;
    }

    public Group findOne(Integer id) {
        Assert.notNull(id, "The id of the group to find must not be null.");

        final User currentUser = currentUserService.getCurrentUser();

        return groupRepository.findById(id)
                .filter(group -> group.getUsers().contains(currentUser))
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Set<User> findUsersOfOne(Integer id) {
        Assert.notNull(id, "The id of the group to find the users of must not be null.");

        final User currentUser = currentUserService.getCurrentUser();

        return groupRepository.findById(id)
                .filter(group -> group.getUsers().contains(currentUser))
                .map(Group::getUsers)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Group updateOne(Integer id, Group group) {
        Assert.notNull(id, "The id of the group to update must not be null.");
        Assert.notNull(group, "The group to update must not be null.");

        final User currentUser = currentUserService.getCurrentUser();

        return groupRepository.findById(id)
                .filter(g -> g.getUsers().contains(currentUser))
                .map(g -> {
                    g.setName(group.getName());

                    // TODO: make sure that we don't wipe all users. - invite mechanic?
                    g.setUsers(group.getUsers());
                    return groupRepository.save(g);
                })
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
}
