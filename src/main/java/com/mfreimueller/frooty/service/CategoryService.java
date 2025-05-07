package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.dto.CategoryDto;
import com.mfreimueller.frooty.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ConversionService conversionService;

    public List<CategoryDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(c -> conversionService.convert(c, CategoryDto.class))
                .toList();
    }

    public Category getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }
}
