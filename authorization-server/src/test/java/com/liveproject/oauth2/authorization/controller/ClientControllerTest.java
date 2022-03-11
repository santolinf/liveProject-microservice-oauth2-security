package com.liveproject.oauth2.authorization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liveproject.oauth2.authorization.server.AuthorizationServerApp;
import com.liveproject.oauth2.authorization.server.controller.dto.ClientDto;
import org.junit.jupiter.api.DisplayName;
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
public class ClientControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("given that the client does not exist in the database, assert that the new client details are added to the database")
    public void givenNewClient_whenAddingClientDetails_thenAssertClientIsAdded() throws Exception {
        ClientDto clientPayload = ClientDto.builder()
                .clientId("client3")
                .secret("secret")
                .scope("read")
                .redirectUri("http://localhost/login")
                .build();

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPayload))
                )
                .andDo(print()).andExpect(status().isCreated());

        mockMvc.perform(get("/clients"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$..clientId").value(hasItems("client3")))
                .andExpect(jsonPath("$..secret").value(hasItems(nullValue())));
    }

    @Test
    @DisplayName("given that the client already exists in the database, assert that attempting to add the same client details is not allowed")
    public void givenExistingClient_whenAddingSameClient_thenReturnError() throws Exception {
        ClientDto clientPayload = ClientDto.builder()
                .clientId("client4")
                .secret("secret")
                .scope("read")
                .redirectUri("http://localhost/login")
                .build();

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPayload))
                )
                .andDo(print()).andExpect(status().isCreated());

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientPayload))
                )
                .andDo(print()).andExpect(status().isBadRequest());
    }
}
