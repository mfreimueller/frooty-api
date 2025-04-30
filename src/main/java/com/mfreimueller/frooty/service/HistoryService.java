package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.History;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.domain.User;
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
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private GroupService groupService;
    @Autowired
    private MealService mealService;
    @Autowired
    private CurrentUserService currentUserService;

    public Stream<WeekDto> findAllOfGroup(Integer groupId) {
        final Group group = groupService.findOne(groupId);

        List<History> historyList = historyRepository.findByGroup(group).orElseThrow();
        HashMap<LocalDate, List<HistoryDto>> groupedHistoryList = new HashMap<>();

        WeekFields wf = WeekFields.of(Locale.getDefault());
        TemporalField weekOfYearField = wf.weekOfYear();
        DayOfWeek firstDayOfWeekField = wf.getFirstDayOfWeek();

        Map<LocalDate, List<HistoryDto>> groupedHistory = historyList.stream()
                .map(HistoryDto::new)
                .collect(Collectors.groupingBy(dto ->
                        dto.getCreatedOn().with(firstDayOfWeekField))
                );

        return groupedHistory.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    LocalDate startOfWeek = entry.getKey();

                    List<HistoryDto> list = entry.getValue();
                    list.sort(Comparator.comparing(HistoryDto::getCreatedOn));

                    int weekOfYear = startOfWeek.get(weekOfYearField);

                    return new WeekDto(startOfWeek, weekOfYear, list);
                });
    }

    public History createOne(Integer groupId, HistoryDto historyDto) {
        final Group group = groupService.findOne(groupId);
        final Meal meal = mealService.findOne(historyDto.getMealId());

        History history = new History(group, meal, historyDto.getCreatedOn(), historyDto.getRating());

        return historyRepository.save(history);
    }

    public History updateOne(Integer groupId, Integer historyId, HistoryDto dto) {
        final User currentUser = currentUserService.getCurrentUser();

        return historyRepository.findById(historyId)
                .filter(h -> Objects.equals(groupId, h.getGroup().getId()) &&
                        h.getGroup().getUsers().contains(currentUser))
                .map(h -> {
                    if (dto.getCreatedOn() != null) {
                        h.setScheduledOn(dto.getCreatedOn());
                    }

                    if (dto.getRating() != null) {
                        h.setRating(dto.getRating());
                    }

                    if (dto.getMealId() != null) {
                        final Meal meal = mealService.findOne(dto.getMealId());
                        h.setMeal(meal);
                    }

                    return historyRepository.save(h);
                })
                .orElseThrow(() -> new EntityNotFoundException(historyId, "history"));
    }
}
