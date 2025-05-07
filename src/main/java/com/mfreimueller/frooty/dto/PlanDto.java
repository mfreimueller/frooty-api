package com.mfreimueller.frooty.dto;

import java.util.List;

public record PlanDto (Integer id, String name, LightUserDto owner, List<LightUserDto> users) {}