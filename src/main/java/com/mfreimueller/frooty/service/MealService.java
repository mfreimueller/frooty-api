package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.MealDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class MealService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private MealRepository mealRepository;

    public Stream<Meal> findAll() {
        return StreamSupport.stream(mealRepository.findAll().spliterator(), false);
    }

    public Meal createOne(MealDto mealDto) {
        Assert.notNull(mealDto, "meal must not be null.");
        Assert.notNull(mealDto.getName(), "A meal name is required.");
        Assert.isTrue(!mealDto.getName().isEmpty(), "A meal name is required.");

        final Category category = categoryService.findOne(mealDto.getCategoryId());
        final Meal meal = new Meal(null, mealDto.getName(), mealDto.getComplexity(), category);

        return mealRepository.save(meal);
    }

    public Meal findOne(Integer id) {
        Assert.notNull(id, "id must not be null.");
        return mealRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }

    public Meal updateOne(Integer id, MealDto mealDto) {
        return mealRepository.findById(id)
                .map(dbMeal -> {
                    if (mealDto.getName() != null && !mealDto.getName().isEmpty()) {
                        dbMeal.setName(mealDto.getName());
                    }

                    // TODO: boundary check: 1 - 10
                    if (mealDto.getComplexity() != null) {
                        dbMeal.setComplexity(mealDto.getComplexity());
                    }

                    return mealRepository.save(dbMeal);
                })
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
}
