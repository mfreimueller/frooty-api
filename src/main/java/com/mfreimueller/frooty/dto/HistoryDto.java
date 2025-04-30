package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.History;

import java.time.LocalDate;

public class HistoryDto {
    private Integer id;
    private Integer groupId;
    private Integer mealId;
    private LocalDate createdOn;
    private Integer rating;

    public HistoryDto() {}

    public HistoryDto(Integer id, Integer groupId, Integer mealId, LocalDate createdOn, Integer rating) {
        this.id = id;
        this.groupId = groupId;
        this.mealId = mealId;
        this.createdOn = createdOn;
        this.rating = rating;
    }

    public HistoryDto(History history) {
        id = history.getId();
        groupId = history.getGroup().getId();
        mealId = history.getMeal().getId();
        createdOn = history.getScheduledOn();
        rating = history.getRating();
    }

    public Integer getId() {
        return id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getMealId() {
        return mealId;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public Integer getRating() {
        return rating;
    }


}
