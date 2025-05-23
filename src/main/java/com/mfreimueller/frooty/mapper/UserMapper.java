package com.mfreimueller.frooty.mapper;

import com.mfreimueller.frooty.domain.User;
import com.mfreimueller.frooty.dto.UserDto;
import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

@Mapper(componentModel = "spring")
public interface UserMapper extends Converter<User, UserDto> {
}
