package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.CategoryDto;
import com.mfreimueller.frooty.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Test
    public void findAll_shouldReturnAllCategories() {
        List<CategoryDto> categories = List.of(
                new CategoryDto(1, "Vegetarisch"),
                new CategoryDto(2, "Suppe")
        );

        when(categoryService.getAllCategories(any(Pageable.class))).thenReturn(categories);

        List<CategoryDto> result = categoryController.findAll(Pageable.unpaged());
        assertEquals(2, result.size());
        assertEquals("Vegetarisch", result.get(0).name());
        verify(categoryService, times(1)).getAllCategories(any(Pageable.class));
    }
}
