package com.mfreimueller.frooty.dto;

import com.mfreimueller.frooty.domain.History;

import java.util.Date;

public class HistoryDto {
    private Integer id;
    private Integer groupId;
    private Integer mealId;
    private Date createdOn;
    private Integer rating;

    public HistoryDto() {}

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

    public Date getCreatedOn() {
        return createdOn;
    }

    public Integer getRating() {
        return rating;
    }


}
