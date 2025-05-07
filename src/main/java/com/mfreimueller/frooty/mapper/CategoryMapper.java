package com.mfreimueller.frooty.mapper;

import com.mfreimueller.frooty.domain.Category;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.CategoryDto;
import com.mfreimueller.frooty.dto.MealDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends Converter<Category, CategoryDto> {

}
