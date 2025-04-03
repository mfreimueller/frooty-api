package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
