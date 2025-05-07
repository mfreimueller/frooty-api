package com.mfreimueller.frooty.dto;

import java.time.LocalDate;
import java.util.List;

public record WeekDto(Integer id, LocalDate startDate, int weekOfYear, List<ScheduledMealDto> scheduledMeals) {
}
