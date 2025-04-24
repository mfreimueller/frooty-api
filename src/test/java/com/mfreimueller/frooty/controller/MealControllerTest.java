package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.service.MealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

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
        Stream<Meal> meals = Stream.of(
                new Meal(1, "Suppe", 2, null),
                new Meal(2, "Brot", 1, null)
        );

        when(mealService.findAll()).thenReturn(meals);

        List<MealDto> result = mealController.findAll();
        assertEquals(2, result.size());
        verify(mealService, times(1)).findAll();
    }

    @Test
    public void createOne_shouldSaveAndReturnMeal() {
        MealDto input = new MealDto(null, "Suppe", 2, null);
        Meal saved = new Meal(1, "Suppe", 2, null);

        when(mealService.createOne(input)).thenReturn(saved);

        MealDto result = mealController.createOne(input);
        assertEquals(1, result.getId());
        assertEquals("Suppe", result.getName());
        verify(mealService, times(1)).createOne(input);
    }

    @Test
    public void findOne_shouldReturnMeal() {
        Meal meal = new Meal(2, "Suppe", 2, null);

        when(mealService.findOne(2)).thenReturn(meal);

        MealDto result = mealController.findOne(2);
        assertEquals(2, result.getId());
        assertEquals("Suppe", result.getName());
        verify(mealService, times(1)).findOne(2);
    }

    @Test
    public void updateOne_shouldSaveAndReturnMeal() {
        MealDto meal = new MealDto(1, "Suppe", 2, null);
        Meal updated = new Meal(1, "Knoblauchsuppe", 3, null);

        when(mealService.updateOne(1, meal)).thenReturn(updated);

        MealDto result = mealController.updateOne(1, meal);
        assertEquals(1, result.getId());
        assertEquals("Knoblauchsuppe", result.getName());
        assertEquals(3, result.getComplexity());

        verify(mealService, times(1)).updateOne(1, meal);
    }
}
