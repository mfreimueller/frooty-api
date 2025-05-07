package com.mfreimueller.frooty.mapper;

import com.mfreimueller.frooty.domain.PlanInvite;
import com.mfreimueller.frooty.dto.PlanInviteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface PlanInviteMapper extends Converter<PlanInvite, PlanInviteDto> {

    @Override
    @Mapping(source = "plan.id", target = "planId")
    @Mapping(source = "invitedUser.id", target = "invitedUserId")
    PlanInviteDto convert(PlanInvite source);
}
