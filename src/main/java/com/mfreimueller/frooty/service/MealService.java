package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.CreateUpdateMealDto;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class MealService {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private ConversionService conversionService;

    public List<MealDto> getAllMeals(Pageable pageable) {
        return mealRepository.findAll(pageable).stream()
                 .map(m -> conversionService.convert(m, MealDto.class))
                 .toList();
    }

    public MealDto createNewMeal(CreateUpdateMealDto createUpdateMealDto) {
        Assert.notNull(createUpdateMealDto, "createUpdateMealDto must not be null.");

        Category category = null;
        if (createUpdateMealDto.categoryId() != null) {
            category = categoryService.getCategoryById(createUpdateMealDto.categoryId());
        }

        final Meal meal = new Meal(createUpdateMealDto.name(), createUpdateMealDto.complexity(), category);
        return conversionService.convert(mealRepository.save(meal), MealDto.class);
    }

    public Meal findOne(Integer id) {
        Assert.notNull(id, "id must not be null.");
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public MealDto updateMeal(Integer mealId, CreateUpdateMealDto createUpdateMealDto) {
        Assert.notNull(mealId, "mealId must not be null.");
        Assert.notNull(createUpdateMealDto, "createUpdateMealDto must not be null.");

        return mealRepository.findById(mealId)
                .map(dbMeal -> {
                    dbMeal.setName(createUpdateMealDto.name());
                    dbMeal.setComplexity(createUpdateMealDto.complexity());

                    Category category = null;
                    if (createUpdateMealDto.categoryId() != null) {
                        category = categoryService.getCategoryById(createUpdateMealDto.categoryId());
                    }

                    dbMeal.setCategory(category);

                    return mealRepository.save(dbMeal);
                })
                .map(m -> conversionService.convert(m, MealDto.class))
                .orElseThrow(() -> new EntityNotFoundException(mealId));
    }
}
