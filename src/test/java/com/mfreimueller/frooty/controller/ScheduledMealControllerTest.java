package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.CreateScheduledMealDto;
import com.mfreimueller.frooty.dto.ScheduledMealDto;
import com.mfreimueller.frooty.dto.UpdateScheduledMealDto;
import com.mfreimueller.frooty.service.ScheduledMealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduledMealControllerTest {
    @InjectMocks
    private ScheduledMealController scheduledMealController;

    @Mock
    private ScheduledMealService scheduledMealService;

    @Test
    public void addMealToCurrentWeek_shouldCallServiceAndReturnDto() {
        final CreateScheduledMealDto input = new CreateScheduledMealDto(1, 2, 0, 3);
        final ScheduledMealDto saved = new ScheduledMealDto(1, 1, 2, 0, 3);

        when(scheduledMealService.createNewEntry(1, 1, input)).thenReturn(saved);

        final ScheduledMealDto result = scheduledMealController.addMealToCurrentWeek(1, 1, input);
        assertEquals(saved.id(), result.id());
        verify(scheduledMealService, times(1)).createNewEntry(1, 1, input);
    }

    @Test
    public void updateMealOfPlan_shouldCallServiceAndReturnDto() {
        final UpdateScheduledMealDto input = new UpdateScheduledMealDto(2, 0, 3);
        final ScheduledMealDto saved = new ScheduledMealDto(1, 1, 2, 0, 3);

        when(scheduledMealService.updateScheduledMeal(1, 1, input)).thenReturn(saved);

        ScheduledMealDto result = scheduledMealController.updateMealOfPlanAndWeek(1, 1, 1, input);
        assertEquals(1, result.id());

        verify(scheduledMealService, times(1)).updateScheduledMeal(1, 1, input);
    }
}
