package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.History;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.GroupDto;
import com.mfreimueller.frooty.dto.HistoryDto;
import com.mfreimueller.frooty.dto.UserDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.GroupRepository;
import com.mfreimueller.frooty.repositories.HistoryRepository;
import com.mfreimueller.frooty.repositories.MealRepository;
import com.mfreimueller.frooty.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryControllerTest {
    @InjectMocks
    private HistoryController historyController;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private HistoryRepository historyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MealRepository mealRepository;

    @Test
    public void getAll_shouldReturnCompleteHistoryOfUser() {
        User user = user();
        Group group = group(user);
        List<History> response = history(group);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
        when(historyRepository.findByGroup(any(Group.class))).thenReturn(Optional.of(response));

        List<HistoryDto> result = historyController.getAll(principal(), group.getId());
        assertEquals(3, result.size());
        assertEquals(1, result.get(0).getMealId());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(groupRepository, times(1)).findById(group.getId());
        verify(historyRepository, times(1)).findByGroup(any(Group.class));
    }

    @Test
    public void createOne_shouldSaveAndReturnGroup() {
        User user = user();
        Group group = group(user);

        History history = history(group).stream().findFirst().orElseThrow();
        History savedHistory = new History(4, history.getGroup(), history.getMeal(), history.getDate(), history.getRating());

        HistoryDto historyDto = new HistoryDto(history);

        when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(historyRepository.save(any(History.class))).thenReturn(savedHistory);
        when(mealRepository.findById(historyDto.getMealId())).thenReturn(Optional.of(history.getMeal()));

        HistoryDto result = historyController.createOne(principal(), group.getId(), historyDto);
        assertEquals(4, result.getId());
        assertEquals(history.getMeal().getId(), result.getMealId());

        verify(groupRepository, times(1)).findById(group.getId());
        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(historyRepository, times(1)).save(any(History.class));
        verify(mealRepository, times(1)).findById(historyDto.getMealId());
    }

    @Test
    public void updateOne_shouldSaveAndReturnGroup() {
        User user = user();
        Group group = group(user);
        History history = history(group).stream().findFirst().orElseThrow();

        HistoryDto input = new HistoryDto(history);

        Meal newMeal = new Meal(3, "Essen #3", 5, Set.of());
        History updated = new History(history.getId(), history.getGroup(), newMeal, history.getDate(), 1);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(historyRepository.findById(input.getId())).thenReturn(Optional.of(history));
        when(historyRepository.save(any(History.class))).thenReturn(updated);
        when(mealRepository.findById(1)).thenReturn(Optional.of(newMeal));

        HistoryDto result = historyController.updateOne(principal(), input.getId(), history.getId(), input);
        assertEquals(history.getId(), result.getId());
        assertEquals(history.getMeal().getId(), newMeal.getId());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(historyRepository, times(1)).findById(input.getId());
        verify(historyRepository, times(1)).save(any(History.class));
        verify(mealRepository, times(1)).findById(1);
    }

    @Test
    public void updateOne_shouldFailOnUnknownId() {
        User user = user();
        Group group = group(user);
        History history = history(group).stream().findFirst().orElseThrow();
        HistoryDto historyDto = new HistoryDto(history);

        when(historyRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> historyController.updateOne(principal(), 1, 1, historyDto));
    }

    @Test
    public void updateOne_shouldFailIfUserIsNotMember() {
        User user = user();
        Group group = group(null);
        History history = history(group).stream().findFirst().orElseThrow();
        HistoryDto historyDto = new HistoryDto(history);

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(historyRepository.findById(history.getId())).thenReturn(Optional.of(history));

        assertThrows(EntityNotFoundException.class,
                () -> historyController.updateOne(principal(), 1, history.getId(), historyDto));
    }

    private Group group(User user) {
        Set<User> users = (user == null) ? Set.of() : Set.of(user);

        return new Group(1, "Testgruppe", users);
    }

    private User user() {
        return new User(1, "user", "pass", Set.of(group(null)));
    }

    private List<History> history(Group group) {
        Meal meal = new Meal(1, "Essen #1", 1, Set.of());

        LocalDate now = LocalDate.now();
        Date today = Date.from(now.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date twoDaysAgo = Date.from(now.minusDays(2).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date weekAgo = Date.from(now.minusWeeks(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        return List.of(
                new History(1, group, meal, weekAgo, 10),
                new History(1, group, new Meal(2, "Essen #2", 5, Set.of()), twoDaysAgo, 3),
                new History(1, group, meal, today, 9)
        );
    }

    private Principal principal() {
        return () -> user().getUsername();
    }
}
