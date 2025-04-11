package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.GroupDto;
import com.mfreimueller.frooty.dto.UserDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.GroupRepository;
import com.mfreimueller.frooty.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {
    @InjectMocks
    private GroupController groupController;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    public void getAll_shouldReturnAllCategoriesOfUser() {
        User user = user();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        List<GroupDto> result = groupController.getAll(principal());
        assertEquals(2, result.size());
        assertEquals("Testgruppe", result.get(0).getName());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    public void createOne_shouldSaveAndReturnGroup() {
        User user = user();
        Group input = new Group(null, "Beste Gruppe", Set.of());
        Group saved = new Group(3, "Beste Gruppe", Set.of());

        HashSet<Group> updatedGroup = new HashSet<>(groups(null));
        updatedGroup.add(input);

        User updatedUser = new User(1, "user", "pass", updatedGroup);

        when(groupRepository.save(input)).thenReturn(saved);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updatedUser);

        GroupDto result = groupController.createOne(principal(), input);
        assertEquals(3, result.getId());
        assertEquals("Beste Gruppe", result.getName());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void findOne_shouldReturnGroup() {
        User user = user();
        Set<Group> groups = groups(user);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(groupRepository.findById(1)).thenReturn(groups.stream().min((g1, g2) -> g1.getId().compareTo(g2.getId())));

        GroupDto result = groupController.findOne(principal(), 1);
        assertEquals(1, result.getId());
        assertEquals("Testgruppe", result.getName());
        verify(groupRepository, times(1)).findById(1);
    }

    @Test
    public void findOne_shouldFailIfUserIsNotMember() {
        User user = user();
        Set<Group> groups = groups(null);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(groupRepository.findById(1)).thenReturn(groups.stream().findFirst());

        assertThrows(EntityNotFoundException.class, () -> groupController.findOne(principal(), 1));
    }

    @Test
    public void findUsersOfOne_shouldReturnUsersOfGroup() {
        User user = user();
        Set<Group> groups = groups(user);
        Optional<Group> group = groups.stream().findFirst();
        assertTrue(group.isPresent());

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(groupRepository.findById(1)).thenReturn(group);

        List<UserDto> result = groupController.findUsersOfOne(principal(), 1);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
        verify(groupRepository, times(1)).findById(1);
    }

    @Test
    public void findUsersOfOne_shouldFailIfUserIsNotMember() {
        User user = user();
        Set<Group> groups = groups(null);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(groupRepository.findById(1)).thenReturn(groups.stream().findFirst());

        assertThrows(EntityNotFoundException.class, () -> groupController.findUsersOfOne(principal(), 1));
    }

    @Test
    public void updateOne_shouldSaveAndReturnGroup() {
        User user = user();
        Set<Group> groups = groups(user);

        Group input = new Group(2, "Beste Gruppe", Set.of(user));
        Group updated = new Group(2, "Beste Gruppe", Set.of(user));

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(groupRepository.findById(2)).thenReturn(groups.stream().filter(x -> x.getId() == 2).findFirst());
        when(groupRepository.save(any(Group.class))).thenReturn(updated);

        GroupDto result = groupController.updateOne(principal(), input.getId(), input);
        assertEquals(2, result.getId());
        assertEquals("Beste Gruppe", result.getName());
        verify(groupRepository, times(1)).save(any(Group.class));
    }

    @Test
    public void updateOne_shouldFailOnUnknownId() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> groupController.updateOne(principal(), 1, new Group(1, "Beste Gruppe", Set.of())));
    }

    @Test
    public void updateOne_shouldFailIfUserIsNotMember() {
        User user = user();
        Set<Group> groups = groups(null);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(groupRepository.findById(1)).thenReturn(groups.stream().findFirst());

        assertThrows(EntityNotFoundException.class,
                () -> groupController.updateOne(principal(), 1, new Group(1, "Beste Gruppe", Set.of())));
    }

    private Set<Group> groups(User user) {
        Set<User> users = (user == null) ? Set.of() : Set.of(user);

        return Set.of(
                new Group(1, "Testgruppe", users),
                new Group(2, "Komm in die Gruppe", users)
        );
    }

    private User user() {
        return new User(1, "user", "pass", groups(null));
    }

    private Principal principal() {
        return () -> user().getUsername();
    }
}
