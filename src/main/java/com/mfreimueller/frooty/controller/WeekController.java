package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.WeekDto;
import com.mfreimueller.frooty.dto.WeekMetaDto;
import com.mfreimueller.frooty.service.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group/{groupId}/week")
public class WeekController {
    @Autowired
    private WeekService weekService;

    @GetMapping("/")
    public WeekMetaDto getMetaInfos(@PathVariable Integer groupId) {
        return weekService.getMetaInfosForGroup(groupId);
    }

    @GetMapping("/{weekId}")
    public WeekDto getWeekById(@PathVariable Integer groupId, @PathVariable Integer weekId) {
        return weekService.map(weekService.getWeekById(groupId, weekId));
    }

    @PostMapping("/")
    public WeekDto createNewWeek(@PathVariable Integer groupId, @RequestBody WeekDto week) {
        return weekService.map(weekService.createNewWeek(groupId, week));
    }

}
