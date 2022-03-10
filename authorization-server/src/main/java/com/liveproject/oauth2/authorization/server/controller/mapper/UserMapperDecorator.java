package com.liveproject.oauth2.authorization.server.controller.mapper;

import com.liveproject.oauth2.authorization.server.controller.dto.UserDto;
import com.liveproject.oauth2.authorization.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserMapperDecorator implements UserMapper {

    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User userDtoToUser(UserDto userDto) {
        User user = delegate.userDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return user;
    }
}

