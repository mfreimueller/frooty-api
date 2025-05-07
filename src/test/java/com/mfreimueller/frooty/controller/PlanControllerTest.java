package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.*;
import com.mfreimueller.frooty.service.PlanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanControllerTest {
    @InjectMocks
    private PlanController planController;

    @Mock
    private PlanService groupService;

    @Test
    public void findAll_shouldReturnAllCategoriesOfUser() {
        final LightUserDto owner = new LightUserDto(1, "max123");

        final List<PlanDto> plans = List.of(
                new PlanDto(1, "Testgruppe", owner, List.of()),
                new PlanDto(2, "Komm in die Gruppe", owner, List.of())
        );

        when(groupService.getAllPlansOfCurrentUser()).thenReturn(plans);

        final List<PlanDto> result = planController.findAll();
        assertEquals(2, result.size());
        assertEquals("Testgruppe", result.get(0).name());
        verify(groupService, times(1)).getAllPlansOfCurrentUser();
    }

    @Test
    public void createOne_shouldSaveAndReturnGroup() {
        final LightUserDto owner = new LightUserDto(1, "max123");

        final CreateUpdatePlanDto input = new CreateUpdatePlanDto("Beste Gruppe");
        final PlanDto saved = new PlanDto(3, "Beste Gruppe", owner, List.of());

        when(groupService.createPlan(input)).thenReturn(saved);

        final PlanDto result = planController.createOne(input);
        assertEquals(3, result.id());
        assertEquals("Beste Gruppe", result.name());
        verify(groupService, times(1)).createPlan(input);
    }

    @Test
    public void updateOne_shouldSaveAndReturnGroup() {
        final LightUserDto owner = new LightUserDto(4, "max123");

        final CreateUpdatePlanDto input = new CreateUpdatePlanDto("Beste Gruppe");
        final PlanDto updated = new PlanDto(2, "Beste Gruppe", owner, List.of());

        when(groupService.updatePlan(2, input)).thenReturn(updated);

        final PlanDto result = planController.updateOne(2, input);
        assertEquals(2, result.id());
        assertEquals("Beste Gruppe", result.name());
        verify(groupService, times(1)).updatePlan(2, input);
    }
}
