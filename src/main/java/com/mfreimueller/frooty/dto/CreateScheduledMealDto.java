package com.mfreimueller.frooty.dto;

public record CreateScheduledMealDto(Integer weekId, Integer mealId, Integer daySlot, Integer weekday) {}