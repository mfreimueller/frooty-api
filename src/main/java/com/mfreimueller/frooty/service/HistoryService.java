package com.mfreimueller.frooty.service;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.History;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.HistoryDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Objects;
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

    public Stream<History> findAllOfGroup(Integer groupId) {
        final Group group = groupService.findOne(groupId);

        return historyRepository.findByGroup(group)
                .orElseThrow()
                .stream()
                .sorted(Comparator.comparing(History::getScheduledOn));
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
