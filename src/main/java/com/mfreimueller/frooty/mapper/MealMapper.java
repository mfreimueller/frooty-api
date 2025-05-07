package com.mfreimueller.frooty.mapper;

import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.dto.MealDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

@Mapper(componentModel = "spring")
public interface MealMapper extends Converter<Meal, MealDto> {

    @Override
    @Mapping(source = "category.id", target = "categoryId")
    MealDto convert(Meal meal);

}
