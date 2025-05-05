package com.mfreimueller.frooty.dto;

import java.util.List;

public record WeekMetaDto(Integer currentWeekId, List<Integer> weekIds) {
}
