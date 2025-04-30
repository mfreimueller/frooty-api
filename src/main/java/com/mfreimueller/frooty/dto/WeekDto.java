package com.mfreimueller.frooty.dto;

import java.time.LocalDate;
import java.util.List;

public record WeekDto(LocalDate startDate, int weekOfYear, List<HistoryDto> historyList) {
}
