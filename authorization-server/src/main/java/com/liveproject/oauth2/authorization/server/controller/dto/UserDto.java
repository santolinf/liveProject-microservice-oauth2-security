package com.liveproject.oauth2.authorization.server.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private long id;
    private String username;
    private String password;

    private List<AuthorityDto> authorities;
}
