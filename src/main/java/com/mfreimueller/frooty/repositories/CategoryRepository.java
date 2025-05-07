package com.mfreimueller.frooty.repositories;

import com.mfreimueller.frooty.domain.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll(Pageable pageable);
}
