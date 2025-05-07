package com.mfreimueller.frooty.mapper;

import com.mfreimueller.frooty.domain.Week;
import com.mfreimueller.frooty.dto.WeekDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Mapper(componentModel = "spring", uses = ScheduledMealMapper.class)
public interface WeekMapper extends Converter<Week, WeekDto> {


    @Override
    @Mapping(expression = "java(getWeekOfYear(week.getStartDate()))", target = "weekOfYear")
    @Mapping(source = "scheduledMeals", target = "scheduledMeals")
    WeekDto convert(Week week);

    default int getWeekOfYear(LocalDate date) {
        return date.get(WeekFields.of(Locale.getDefault()).weekOfYear());
    }
}
