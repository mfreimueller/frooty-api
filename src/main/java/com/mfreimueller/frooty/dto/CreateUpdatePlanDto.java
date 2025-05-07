package com.mfreimueller.frooty.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateUpdatePlanDto(@NotEmpty String name) {}
