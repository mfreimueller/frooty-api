package com.mfreimueller.frooty.mapper;

import com.mfreimueller.frooty.domain.ScheduledMeal;
import com.mfreimueller.frooty.dto.ScheduledMealDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface ScheduledMealMapper extends Converter<ScheduledMeal, ScheduledMealDto> {

    @Override
    @Mapping(source = "week.id", target = "weekId")
    @Mapping(source = "meal.id", target = "mealId")
    ScheduledMealDto convert(ScheduledMeal source);
}
