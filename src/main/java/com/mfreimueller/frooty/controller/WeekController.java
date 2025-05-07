package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.annotations.CheckPlanAccess;
import com.mfreimueller.frooty.dto.CreateWeekDto;
import com.mfreimueller.frooty.dto.WeekDto;
import com.mfreimueller.frooty.service.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan/{planId}/week")
public class WeekController {
    @Autowired
    private WeekService weekService;

    @GetMapping
    @CheckPlanAccess(planIdParam = "planId")
    public List<WeekDto> getAllWeeks(@PathVariable Integer planId, Pageable pageable) {
        return weekService.getWeeksForPlan(planId, pageable);
    }

    @GetMapping("/{weekId}")
    @CheckPlanAccess(planIdParam = "planId")
    public WeekDto getWeekById(@PathVariable Integer planId, @PathVariable Integer weekId) {
        return weekService.getWeekDtoById(planId, weekId);
    }

    @PostMapping
    @CheckPlanAccess(planIdParam = "planId")
    public WeekDto createNewWeek(@PathVariable Integer planId, @RequestBody CreateWeekDto createWeekDto) {
        return weekService.createNewWeek(planId, createWeekDto);
    }

}
