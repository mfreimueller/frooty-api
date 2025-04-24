package com.mfreimueller.frooty.controller;

import com.mfreimueller.frooty.dto.HistoryDto;
import com.mfreimueller.frooty.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @GetMapping("/{groupId}")
    public List<HistoryDto> findAll(@PathVariable Integer groupId) {
        return historyService.findAllOfGroup(groupId)
                .map(HistoryDto::new)
                .toList();
    }

    @PostMapping("/{groupId}")
    public HistoryDto createOne(@PathVariable Integer groupId, @RequestBody HistoryDto dto) {
        return new HistoryDto(historyService.createOne(groupId, dto));
    }

    @PatchMapping("/{groupId}/{historyId}")
    public HistoryDto updateOne(@PathVariable Integer groupId, @PathVariable Integer historyId, @RequestBody HistoryDto dto) {
        return new HistoryDto(historyService.updateOne(groupId, historyId, dto));
    }
}
