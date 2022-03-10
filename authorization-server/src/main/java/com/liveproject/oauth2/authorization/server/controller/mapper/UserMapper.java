package com.liveproject.oauth2.authorization.server.controller.mapper;

import com.liveproject.oauth2.authorization.server.controller.dto.AuthorityDto;
import com.liveproject.oauth2.authorization.server.controller.dto.UserDto;
import com.liveproject.oauth2.authorization.server.model.Authority;
import com.liveproject.oauth2.authorization.server.model.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    Collection<UserDto> usersToUserDtos(Collection<User> users);

    @Mapping(target = "password", ignore = true)
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Authority authorityDtoToAuthority(AuthorityDto authorityDto);
}
