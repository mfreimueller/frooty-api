package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.HistoryEntry;

public class HistoryDto {
    private Integer id;
    private Integer weekId;
    private Integer mealId;
    private Integer order;
    private Integer day;

    public HistoryDto() {}

    public HistoryDto(Integer id, Integer weekId, Integer mealId, Integer order, Integer day) {
        this.id = id;
        this.weekId = weekId;
        this.mealId = mealId;
        this.order = order;
        this.day = day;
    }

    public HistoryDto(HistoryEntry historyEntry) {
        id = historyEntry.getId();
        weekId = historyEntry.getWeek().getId();
        mealId = historyEntry.getMeal().getId();
        order = historyEntry.getOrder();
        day = historyEntry.getDay();
    }

    public Integer getId() {
        return id;
    }

    public Integer getWeekId() {
        return weekId;
    }

    public Integer getMealId() {
        return mealId;
    }

    public Integer getOrder() {
        return order;
    }

    public Integer getDay() {
        return day;
    }
}
