package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.History;

import java.util.Date;

public class HistoryDto {
    private Integer id;
    private Integer groupId;
    private Integer mealId;
    private Date date;
    private Integer rating;

    public HistoryDto() {}

    public HistoryDto(History history) {
        id = history.getId();
        groupId = history.getGroup().getId();
        mealId = history.getMeal().getId();
        date = history.getDate();
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

    public Date getDate() {
        return date;
    }

    public Integer getRating() {
        return rating;
    }
}
