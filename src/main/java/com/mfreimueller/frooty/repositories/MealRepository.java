package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Meal;
import org.springframework.data.repository.CrudRepository;

public interface MealRepository extends CrudRepository<Meal, Integer> {
}
