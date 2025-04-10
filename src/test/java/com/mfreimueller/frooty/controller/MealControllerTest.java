package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.MealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealControllerTest {
    @InjectMocks
    private MealController mealController;

    @Mock
    private MealRepository mealRepository;

    @Test
    public void getAll_shouldReturnAllMeals() {
        List<Meal> meals = List.of(
                new Meal(1, "Suppe", 2, Set.of()),
                new Meal(2, "Brot", 1, Set.of())
        );

        when(mealRepository.findAll()).thenReturn(meals);

        List<MealDto> result = mealController.getAll();
        assertEquals(2, result.size());
        assertEquals("Suppe", result.get(0).getName());
        verify(mealRepository, times(1)).findAll();
    }

    @Test
    public void createOne_shouldSaveAndReturnMeal() {
        Meal input = new Meal("Suppe", 2, Set.of());
        Meal saved = new Meal(1, "Suppe", 2, Set.of());

        when(mealRepository.save(input)).thenReturn(saved);

        MealDto result = mealController.createOne(input);
        assertEquals(1, result.getId());
        assertEquals("Suppe", result.getName());
        verify(mealRepository, times(1)).save(input);
    }

    @Test
    public void findOne_shouldReturnMeal() {
        Meal meal = new Meal(2, "Suppe", 2, Set.of());
        Optional<Meal> returnMeal = Optional.of(meal);

        when(mealRepository.findById(2)).thenReturn(returnMeal);

        MealDto result = mealController.findOne(2);
        assertEquals(2, result.getId());
        assertEquals("Suppe", result.getName());
        verify(mealRepository, times(1)).findById(2);
    }

    @Test
    public void findOne_shouldThrowAnExceptionOnUnknownId() {
        when(mealRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> mealController.findOne(1));
    }

    @Test
    public void updateOne_shouldSaveAndReturnMeal() {
        Meal meal = new Meal(1, "Suppe", 2, null);
        Meal updated = new Meal(1, "Knoblauchsuppe", 3, Set.of());

        when(mealRepository.findById(1)).thenReturn(Optional.of(meal));
        when(mealRepository.save(any(Meal.class))).thenReturn(updated);

        MealDto result = mealController.updateOne(1, meal);
        assertEquals(1, result.getId());
        assertEquals("Knoblauchsuppe", result.getName());
        assertEquals(3, result.getComplexity());

        verify(mealRepository, times(1)).save(meal);
    }

    @Test
    public void updateOne_shouldThrowAnExceptionOnUnknownId() {
        Meal meal = new Meal(1, "Suppe", 2, Set.of());

        when(mealRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> mealController.updateOne(1, meal));
    }
}
