package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.*;
import com.mfreimueller.frooty.dto.HistoryDto;
import com.mfreimueller.frooty.dto.WeekDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HistoryService extends EntityService<HistoryEntry, HistoryDto> {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private MealService mealService;
    @Autowired
    private WeekService weekService;

    public HistoryEntry createNewEntry(Integer groupId, Integer weekId, HistoryDto historyEntry) {
        throwIfUserNotPartOfGroup(groupId);

        Week week = weekService.getWeekById(groupId, weekId);
        final Meal meal = mealService.findOne(historyEntry.getMealId());

        HistoryEntry entry = new HistoryEntry(week, meal, historyEntry.getOrder(), historyEntry.getDay());
        return historyRepository.save(entry);
    }

    @Override
    public HistoryDto map(HistoryEntry entry) {
        return new HistoryDto(entry.getId(), entry.getWeek().getId(), entry.getMeal().getId(), entry.getOrder(), entry.getDay());
    }
}
