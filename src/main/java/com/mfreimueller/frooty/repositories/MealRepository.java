package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Meal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MealRepository extends CrudRepository<Meal, Integer> {
    List<Meal> findAll(Pageable pageable);
}
