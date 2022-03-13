package com.liveproject.oauth2.authorization.server.controller;

import com.liveproject.oauth2.authorization.server.BaseTests;
import com.liveproject.oauth2.authorization.server.controller.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseTests {

    @Test
    @DisplayName("given that the user does not exist in the database, assert that the new user details are added to the database")
    public void givenNewUser_whenAddingUserDetails_thenAssertUserIsAdded() throws Exception {
        UserDto userPayload = UserDto.builder()
                .username("user3")
                .password("password")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPayload))
                )
                .andDo(print()).andExpect(status().isCreated());

        mockMvc.perform(get("/users"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(hasItems("user3")))
                .andExpect(jsonPath("$..password").value(hasItems(nullValue())));
    }

    @Test
    @DisplayName("given that the user already exists in the database, assert that attempting to add the same user details is not allowed")
    public void givenExistingUser_whenAddingSameUser_thenReturnError() throws Exception {
        UserDto userPayload = UserDto.builder()
                .username("user4")
                .password("password")
                .build();

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPayload))
                )
                .andDo(print()).andExpect(status().isCreated());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userPayload))
                )
                .andDo(print()).andExpect(status().isBadRequest());
    }
}
