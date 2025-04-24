package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.History;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.HistoryDto;
import com.mfreimueller.frooty.service.HistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HistoryControllerTest {
    @InjectMocks
    private HistoryController historyController;

    @Mock
    private HistoryService historyService;

    @Test
    public void findAll_shouldReturnCompleteHistoryOfUser() {
        Stream<History> response = Stream.of(
                new History(1, new Group(1, "Test", Set.of()), new Meal(), new Date(), 10),
                new History(1, new Group(1, "Test", Set.of()), new Meal(), new Date(), 3)
        );

        when(historyService.findAllOfGroup(1)).thenReturn(response);

        List<HistoryDto> result = historyController.findAll(1);
        assertEquals(2, result.size());
        verify(historyService, times(1)).findAllOfGroup(1);
    }

    @Test
    public void createOne_shouldSaveAndReturnGroup() {
        HistoryDto inputDto = new HistoryDto(null, 1, 1, new Date(), 1);
        History savedHistory = new History(4, new Group(), new Meal(), new Date(), 1);

        when(historyService.createOne(1, inputDto)).thenReturn(savedHistory);

        HistoryDto result = historyController.createOne(1, inputDto);
        assertEquals(4, result.getId());

        verify(historyService, times(1)).createOne(1, inputDto);
    }

    @Test
    public void updateOne_shouldSaveAndReturnGroup() {
        HistoryDto inputDto = new HistoryDto(1, 1, 1, new Date(), 5);

        Meal newMeal = new Meal(3, "Essen #3", 5, null);
        History updated = new History(1, new Group(1, "Beste Gruppe", Set.of()), newMeal, new Date(), 5);

        when(historyService.updateOne(1, 1, inputDto)).thenReturn(updated);

        HistoryDto result = historyController.updateOne(1, 1, inputDto);
        assertEquals(inputDto.getId(), result.getId());
        assertEquals(result.getMealId(), newMeal.getId());

        verify(historyService, times(1)).updateOne(1, 1, inputDto);
    }
}
