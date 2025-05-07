package com.mfreimueller.frooty.mapper;

import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.LightUserDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface LightUserMapper extends Converter<User, LightUserDto> {

}
