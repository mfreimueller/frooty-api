package com.mfreimueller.frooty.mapper;

import com.mfreimueller.frooty.domain.Plan;
import com.mfreimueller.frooty.dto.PlanDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface PlanMapper extends Converter<Plan, PlanDto> {

}
