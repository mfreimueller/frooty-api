package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.domain.Group;
import com.mfreimueller.frooty.domain.History;
import com.mfreimueller.frooty.domain.Meal;
import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.GroupDto;
import com.mfreimueller.frooty.dto.HistoryDto;
import com.mfreimueller.frooty.dto.UserDto;
import com.mfreimueller.frooty.exception.EntityNotFoundException;
import com.mfreimueller.frooty.repositories.GroupRepository;
import com.mfreimueller.frooty.repositories.HistoryRepository;
import com.mfreimueller.frooty.repositories.MealRepository;
import com.mfreimueller.frooty.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MealRepository mealRepository;

    @GetMapping("/{groupId}")
    public List<HistoryDto> getAll(Principal principal, @PathVariable Integer groupId) {
        return historyRepository.findByGroup(group(principal, groupId))
                .map(s ->
                        s.stream()
                                .map(HistoryDto::new)
                                .sorted((h1, h2) -> h1.getDate().compareTo(h2.getDate()))
                                .toList())
                .orElseThrow();
    }

    @PostMapping("/{groupId}")
    public HistoryDto createOne(Principal principal, @PathVariable Integer groupId, @RequestBody HistoryDto dto) {
        Group group = group(principal, groupId);
        History history = new History(group, meal(dto.getMealId()), dto.getDate(), dto.getRating());

        return new HistoryDto(historyRepository.save(history));
    }

    @PatchMapping("/{groupId}/{historyId}")
    public HistoryDto updateOne(Principal principal, @PathVariable Integer groupId, @PathVariable Integer historyId, @RequestBody HistoryDto dto) {
        return historyRepository.findById(historyId)
                .filter(h -> Objects.equals(groupId, h.getGroup().getId()) &&
                        h.getGroup().getUsers().contains(user(principal)))
                .map(h -> {
                    if (dto.getDate() != null) {
                        h.setDate(dto.getDate());
                    }

                    if (dto.getRating() != null) {
                        h.setRating(dto.getRating());
                    }

                    if (dto.getMealId() != null) {
                        h.setMeal(meal(dto.getMealId()));
                    }

                    return new HistoryDto(historyRepository.save(h));
                })
                .orElseThrow(() -> new EntityNotFoundException(historyId, "history"));
    }

    private Meal meal(Integer mealId) {
        return mealRepository.findById(mealId).orElseThrow(() -> new EntityNotFoundException(mealId, "meal"));
    }

    private Group group(Principal principal, Integer groupId) {
        final User user = user(principal);

        return groupRepository.findById(groupId)
                .filter(g -> g.getUsers().contains(user))
                .orElseThrow(() -> new EntityNotFoundException(groupId));
    }

    private User user(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElseThrow();
    }
}
