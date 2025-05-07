package com.mfreimueller.frooty.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public record CreateUpdateMealDto(@NotEmpty String name, @Min(0) @Max(10) Integer complexity, Integer categoryId) {}
