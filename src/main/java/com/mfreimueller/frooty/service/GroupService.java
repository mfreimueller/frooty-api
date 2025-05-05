package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.GroupDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.GroupRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private CurrentUserService currentUserService;

    public boolean isUserInGroup(Integer userId, Integer groupId) {
        return groupRepository.existsByIdAndUsers_Id(groupId, userId);
    }

    public List<Group> findAll() {
        final User currentUser = currentUserService.getCurrentUser();

        return currentUser.getGroups()
                .stream()
                .sorted((g1, g2) -> g1.getId().compareTo(g2.getId()))
                .toList();
    }

    public Group createOne(GroupDto groupDto) {
        Assert.notNull(groupDto, "group must not be null.");
        Assert.isNull(groupDto.getId(), "The id of the group to create must be null.");
        Assert.notNull(groupDto.getName(), "name must not be null.");
        Assert.isTrue(!groupDto.getName().isEmpty(), "name must not be empty.");

        final User currentUser = currentUserService.getCurrentUser();

        // TODO: verify that this way the user is added to the group!
        final Group groupToCreate = new Group(groupDto.getName(), Set.of(currentUser));
        return groupRepository.save(groupToCreate);
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

    public Group updateOne(Integer id, GroupDto groupDto) {
        Assert.notNull(id, "id must not be null.");
        Assert.notNull(groupDto, "group must not be null.");

        final User currentUser = currentUserService.getCurrentUser();

        return groupRepository.findById(id)
                .filter(g -> g.getUsers().contains(currentUser))
                .map(g -> {
                    if (groupDto.getName() != null && !groupDto.getName().isEmpty()) {
                        g.setName(groupDto.getName());
                    }

                    // TODO: invite mechanic.

                    return groupRepository.save(g);
                })
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
}
