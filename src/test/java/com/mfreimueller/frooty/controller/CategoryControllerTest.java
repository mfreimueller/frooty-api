package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.CategoryDto;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void getAll_shouldReturnAllCategories() {
        List<Category> categories = List.of(
                new Category("Vegetarisch", Set.of()),
                new Category("Suppe", Set.of())
        );

        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDto> result = categoryController.getAll();
        assertEquals(2, result.size());
        assertEquals("Vegetarisch", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void createOne_shouldSaveAndReturnCategory() {
        Category input = new Category("Suppe", Set.of());
        Category saved = new Category(1, "Suppe", Set.of());

        when(categoryRepository.save(input)).thenReturn(saved);

        CategoryDto result = categoryController.createOne(input);
        assertEquals(1, result.getId());
        assertEquals("Suppe", result.getName());
        verify(categoryRepository, times(1)).save(input);
    }

    @Test
    public void findOne_shouldReturnCategory() {
        List<Category> categories = List.of(
                new Category(1, "Vegetarisch", Set.of()),
                new Category(2, "Suppe", Set.of())
        );
        Optional<Category> returnCategory = Optional.of(categories.get(1));

        when(categoryRepository.findById(2)).thenReturn(returnCategory);

        CategoryDto result = categoryController.findOne(2);
        assertEquals(2, result.getId());
        assertEquals("Suppe", result.getName());
        verify(categoryRepository, times(1)).findById(2);
    }

    @Test
    public void findMealsOfOne_shouldReturnMealsOfCategory() {
        List<Category> categories = List.of(
                new Category(1, "Vegetarisch", Set.of()),
                new Category(2, "Suppe", Set.of(
                        new Meal(1, "Klare Suppe", 1, null),
                        new Meal(2, "Tomatensuppe", 4, null)
                ))
        );
        Optional<Category> returnCategory = Optional.of(categories.get(1));

        when(categoryRepository.findById(2)).thenReturn(returnCategory);

        List<MealDto> result = categoryController.findMealsOfOne(2);
        assertEquals(2, result.size());
        assertEquals("Klare Suppe", result.get(0).getName());
        verify(categoryRepository, times(1)).findById(2);
    }

    @Test
    public void findMealsOfOne_shouldThrowAnExceptionOnUnknownId() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> categoryController.findMealsOfOne(1));
    }

    @Test
    public void updateOne_shouldSaveAndReturnCategory() {
        List<Category> categories = List.of(
                new Category(1, "Supep", Set.of())
        );
        Optional<Category> returnCategory = Optional.of(categories.get(0));

        when(categoryRepository.findById(1)).thenReturn(returnCategory);

        Category saved = new Category(1, "Suppe", Set.of());
        when(categoryRepository.save(categories.get(0))).thenReturn(saved);

        CategoryDto result = categoryController.updateOne(1, categories.get(0));
        assertEquals(1, result.getId());
        assertEquals("Suppe", result.getName());
        verify(categoryRepository, times(1)).save(categories.get(0));
    }

    @Test
    public void updateOne_shouldThrowAnExceptionOnUnknownId() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> categoryController.updateOne(1, new Category(1, "Suppe", Set.of())));
    }
}
