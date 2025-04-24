package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.CategoryDto;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Test
    public void getAll_shouldReturnAllCategories() {
        List<Category> categories = List.of(
                new Category("Vegetarisch", Set.of()),
                new Category("Suppe", Set.of())
        );

        when(categoryService.findAll()).thenReturn(categories);

        List<CategoryDto> result = categoryController.findAll();
        assertEquals(2, result.size());
        assertEquals("Vegetarisch", result.get(0).getName());
        verify(categoryService, times(1)).findAll();
    }

    @Test
    public void createOne_shouldSaveAndReturnCategory() {
        Category input = new Category("Suppe", Set.of());
        Category saved = new Category(1, "Suppe", Set.of());

        when(categoryService.createOne(input)).thenReturn(saved);

        CategoryDto result = categoryController.createOne(input);
        assertEquals(1, result.getId());
        assertEquals("Suppe", result.getName());
        verify(categoryService, times(1)).createOne(input);
    }

    @Test
    public void findOne_shouldReturnCategory() {
        List<Category> categories = List.of(
                new Category(1, "Vegetarisch", Set.of()),
                new Category(2, "Suppe", Set.of())
        );
        Category returnCategory = categories.get(1);

        when(categoryService.findOne(2)).thenReturn(returnCategory);

        CategoryDto result = categoryController.findOne(2);
        assertEquals(2, result.getId());
        assertEquals("Suppe", result.getName());
        verify(categoryService, times(1)).findOne(2);
    }

    @Test
    public void findMealsOfOne_shouldReturnMealsOfCategory() {
        List<Meal> meals = List.of(
            new Meal(1, "Klare Suppe", 1, null),
            new Meal(2, "Tomatensuppe", 4, null)
        );

        when(categoryService.findMealsOfOne(2)).thenReturn(meals);

        List<MealDto> result = categoryController.findMealsOfOne(2);
        assertEquals(2, result.size());
        assertEquals("Klare Suppe", result.get(0).getName());
        verify(categoryService, times(1)).findMealsOfOne(2);
    }

    @Test
    public void findMealsOfOne_shouldThrowAnExceptionOnUnknownId() {
        when(categoryService.findMealsOfOne(1)).thenThrow(new EntityNotFoundException(1, "Category"));

        assertThrows(EntityNotFoundException.class,
                () -> categoryController.findMealsOfOne(1));
    }

    @Test
    public void updateOne_shouldSaveAndReturnCategory() {
        Category categoryToUpdate = new Category(1, "Supep", Set.of());
        Category updatedCategory = new Category(1, "Suppe", Set.of());

        when(categoryService.updateOne(1, categoryToUpdate)).thenReturn(updatedCategory);

        CategoryDto result = categoryController.updateOne(1, categoryToUpdate);
        assertEquals(1, result.getId());
        assertEquals("Suppe", result.getName());

        verify(categoryService, times(1)).updateOne(1, categoryToUpdate);
    }

    @Test
    public void updateOne_shouldThrowAnExceptionOnUnknownId() {
        Category categoryToUpdate = new Category(1, "Supep", Set.of());
        when(categoryService.updateOne(1, categoryToUpdate)).thenThrow(new EntityNotFoundException(1, "Controller"));

        assertThrows(EntityNotFoundException.class,
                () -> categoryController.updateOne(1, categoryToUpdate));
    }
}
