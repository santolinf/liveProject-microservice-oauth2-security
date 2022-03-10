package com.liveproject.oauth2.authorization.server.controller;

import com.liveproject.oauth2.authorization.server.controller.dto.UserDto;
import com.liveproject.oauth2.authorization.server.controller.mapper.UserMapper;
import com.liveproject.oauth2.authorization.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper mapper;
    private final UserService userService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody UserDto userDto) {
        userService.addUser(mapper.userDtoToUser(userDto));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<UserDto> getUsers() {
        return mapper.usersToUserDtos(userService.getUsers());
    }
}
