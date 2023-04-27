package ru.clevertec.ecl.mapping;


import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.UserDto;
import ru.clevertec.ecl.entities.User;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    List<UserDto> toDtoList(List<User> userList);
}
