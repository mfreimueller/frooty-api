package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.CreateUpdateMealDto;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.service.MealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealControllerTest {
    @InjectMocks
    private MealController mealController;

    @Mock
    private MealService mealService;

    @Test
    public void getAll_shouldReturnAllMeals() {
        List<MealDto> meals = List.of(
                new MealDto(1, "Suppe", 2, null),
                new MealDto(2, "Brot", 1, null)
        );

        when(mealService.getAllMeals(any(Pageable.class))).thenReturn(meals);

        List<MealDto> result = mealController.findAll(Pageable.unpaged());
        assertEquals(2, result.size());
        verify(mealService, times(1)).getAllMeals(any(Pageable.class));
    }

    @Test
    public void createNewMeal_shouldCallServiceAndReturnDto() {
        CreateUpdateMealDto input = new CreateUpdateMealDto("Suppe", 2, null);
        MealDto saved = new MealDto(1, "Suppe", 2, null);

        when(mealService.createNewMeal(input)).thenReturn(saved);

        MealDto result = mealController.createNewMeal(input);
        assertEquals(1, result.id());
        assertEquals("Suppe", result.name());
        verify(mealService, times(1)).createNewMeal(input);
    }

    @Test
    public void updateOne_shouldCallServiceAndReturnDto() {
        CreateUpdateMealDto meal = new CreateUpdateMealDto("Suppe", 2, null);
        MealDto updated = new MealDto(1, "Knoblauchsuppe", 3, null);

        when(mealService.updateMeal(1, meal)).thenReturn(updated);

        MealDto result = mealController.updateMeal(1, meal);
        assertEquals(1, result.id());
        assertEquals("Knoblauchsuppe", result.name());
        assertEquals(3, result.complexity());

        verify(mealService, times(1)).updateMeal(1, meal);
    }
}
