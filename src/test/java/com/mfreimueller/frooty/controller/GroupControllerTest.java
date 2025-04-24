package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.GroupDto;
import com.mfreimueller.frooty.dto.UserDto;
import com.mfreimueller.frooty.service.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {
    @InjectMocks
    private GroupController groupController;

    @Mock
    private GroupService groupService;

    @Test
    public void findAll_shouldReturnAllCategoriesOfUser() {
        List<Group> groups = List.of(
                new Group(1, "Testgruppe", Set.of()),
                new Group(2, "Komm in die Gruppe", Set.of())
        );

        when(groupService.findAll()).thenReturn(groups);

        List<GroupDto> result = groupController.findAll();
        assertEquals(2, result.size());
        assertEquals("Testgruppe", result.get(0).getName());
        verify(groupService, times(1)).findAll();
    }

    @Test
    public void createOne_shouldSaveAndReturnGroup() {
        GroupDto input = new GroupDto(null, "Beste Gruppe", Set.of());
        Group saved = new Group(3, "Beste Gruppe", Set.of());

        when(groupService.createOne(input)).thenReturn(saved);

        GroupDto result = groupController.createOne(input);
        assertEquals(3, result.getId());
        assertEquals("Beste Gruppe", result.getName());
        verify(groupService, times(1)).createOne(input);
    }

    @Test
    public void findOne_shouldReturnGroup() {
        Group group = new Group(1, "Beste Gruppe", Set.of());

        when(groupService.findOne(1)).thenReturn(group);

        GroupDto result = groupController.findOne(1);
        assertEquals(1, result.getId());
        assertEquals("Beste Gruppe", result.getName());
        verify(groupService, times(1)).findOne(1);
    }

    @Test
    public void findUsersOfOne_shouldReturnUsersOfGroup() {
        Set<User> users = Set.of(
                new User(1, "Alice", "123", Set.of()),
                new User(2, "Bob", "root", Set.of())
        );

        when(groupService.findUsersOfOne(1)).thenReturn(users);

        List<UserDto> result = groupController.findUsersOfOne(1);
        assertEquals(2, result.size());
        verify(groupService, times(1)).findUsersOfOne(1);
    }

    @Test
    public void updateOne_shouldSaveAndReturnGroup() {
        GroupDto input = new GroupDto(2, "Beste Gruppe", Set.of());
        Group updated = new Group(2, "Beste Gruppe", Set.of());

        when(groupService.updateOne(2, input)).thenReturn(updated);

        GroupDto result = groupController.updateOne(input.getId(), input);
        assertEquals(2, result.getId());
        assertEquals("Beste Gruppe", result.getName());
        verify(groupService, times(1)).updateOne(2, input);
    }
}
