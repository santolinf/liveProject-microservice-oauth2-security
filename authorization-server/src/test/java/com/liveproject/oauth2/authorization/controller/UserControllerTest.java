package com.liveproject.oauth2.authorization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liveproject.oauth2.authorization.server.AuthorizationServerApp;
import com.liveproject.oauth2.authorization.server.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(classes = AuthorizationServerApp.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenNewUser_whenGettingUserDetails_thenReturnUserDetails() throws Exception {
        var username = "user3";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(User.builder().username(username).password("password").build()))
                )
                .andDo(print()).andExpect(status().isCreated());

        mockMvc.perform(get("/users"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$..username").value(hasItems(username)))
                .andExpect(jsonPath("$..password").value(hasItems(nullValue())));
    }

    @Test
    public void givenExistingUser_whenAddingSameUser_thenReturnError() throws Exception {
        var username = "user4";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(User.builder().username(username).password("password").build()))
                )
                .andDo(print()).andExpect(status().isCreated());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(User.builder().username(username).password("password").build()))
                )
                .andDo(print()).andExpect(status().isBadRequest());
    }
}
