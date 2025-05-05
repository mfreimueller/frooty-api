package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.HistoryDto;
import com.mfreimueller.frooty.dto.WeekDto;
import com.mfreimueller.frooty.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/group/{groupId}/week/{weekId}/entry")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @PostMapping("/")
    public HistoryDto addEntryToCurrentWeek(@PathVariable Integer groupId, @PathVariable Integer weekId, @RequestBody HistoryDto historyEntry) {
        return historyService.map(historyService.createNewEntry(groupId, weekId, historyEntry));
    }

    @PutMapping("/{entryId}")
    public WeekDto updateEntryOfCurrentWeek(@PathVariable Integer groupId, @PathVariable Integer weekId, @PathVariable Integer entryId) {
        return null;
    }

    @DeleteMapping("/{entryId}")
    public WeekDto deleteEntryOfCurrentWeek(@PathVariable Integer groupId, @PathVariable Integer weekId, @PathVariable Integer entryId) {
        return null;
    }
}
