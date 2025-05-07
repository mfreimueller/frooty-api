package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.CategoryDto;
import com.mfreimueller.frooty.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryService.getAllCategories(pageable);
    }
}
