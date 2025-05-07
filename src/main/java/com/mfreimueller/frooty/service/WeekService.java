package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.*;
import com.mfreimueller.frooty.dto.CreateWeekDto;
import com.mfreimueller.frooty.dto.WeekDto;
import com.mfreimueller.frooty.repositories.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class WeekService {
    @Autowired
    private WeekRepository weekRepository;
    @Autowired
    private PlanService planService;
    @Autowired
    private ConversionService conversionService;

    public List<WeekDto> getWeeksForPlan(Integer planId, Pageable pageable) {
        return weekRepository.findAllByPlanId(planId, pageable).stream()
                .map(w -> conversionService.convert(w, WeekDto.class))
                .toList();
    }

    public Week getWeekById(Integer planId, Integer weekId) {
        return weekRepository.findById(weekId).orElseThrow();
    }

    public WeekDto getWeekDtoById(Integer planId, Integer weekId) {
        return conversionService.convert(getWeekById(planId, weekId), WeekDto.class);
    }

    public WeekDto createNewWeek(Integer planId, CreateWeekDto createWeekDto) {
        TemporalField dayOfWeekField = WeekFields.of(Locale.getDefault()).dayOfWeek();
        LocalDate startDate = createWeekDto.startDate();
        LocalDate startOfWeek = startDate.with(dayOfWeekField, 1);

        if (weekRepository.existsByPlan_IdAndStartDate(planId, startOfWeek)) {
            throw new IllegalArgumentException("A week starting at " + startOfWeek + " already exists for this plan!");
        }

        final Plan plan = planService.findPlan(planId);
        return conversionService.convert(weekRepository.save(new Week(plan, startOfWeek)), WeekDto.class);
    }
}
