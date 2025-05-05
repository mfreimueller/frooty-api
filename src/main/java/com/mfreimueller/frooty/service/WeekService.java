package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.*;
import com.mfreimueller.frooty.dto.HistoryDto;
import com.mfreimueller.frooty.dto.WeekDto;
import com.mfreimueller.frooty.dto.WeekMetaDto;
import com.mfreimueller.frooty.repositories.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class WeekService extends EntityService<Week, WeekDto> {
    @Autowired
    private WeekRepository weekRepository;
    @Autowired
    private MealService mealService;
    @Autowired
    private HistoryService historyService;

    public WeekMetaDto getMetaInfosForGroup(Integer groupId) {
        throwIfUserNotPartOfGroup(groupId);

        final List<Integer> weekIdsOfGroup = weekRepository.findIdsByGroupId(groupId);
        return new WeekMetaDto(weekIdsOfGroup.get(0), weekIdsOfGroup.subList(1, weekIdsOfGroup.size()));
    }

    public Week getWeekById(Integer groupId, Integer weekId) {
        throwIfUserNotPartOfGroup(groupId);

        return weekRepository.findById(weekId).orElseThrow();
    }

    public Week createNewWeek(Integer groupId, WeekDto weekDto) {
        throwIfUserNotPartOfGroup(groupId);

        final Group group = groupService.findOne(groupId);
        // TODO: make sure that weeks don't overlap
        // TODO: make sure that startDate is a valid begin of week
        final Week week = weekRepository.save(new Week(group, weekDto.startDate(), Set.of()));

        List<HistoryEntry> historyList = weekDto.historyList()
                .stream()
                .map(e ->
                    historyService.createNewEntry(groupId, week.getId(), e)
                )
                .toList();

        week.getHistoryEntries().addAll(historyList);
        return week;
    }

    @Override
    public WeekDto map(Week week) {
        final TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfYear();
        return new WeekDto(
                week.getId(),
                week.getStartDate(),
                week.getStartDate().get(weekOfYear),
                week.getHistoryEntries().stream()
                        .map(HistoryDto::new)
                        .toList()
        );
    }
}
