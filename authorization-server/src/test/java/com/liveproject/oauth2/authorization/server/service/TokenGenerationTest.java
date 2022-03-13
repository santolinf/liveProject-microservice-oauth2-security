package com.liveproject.oauth2.authorization.server.service;

import com.liveproject.oauth2.authorization.server.BaseTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TokenGenerationTest extends BaseTests {

    @Test
    @DisplayName("Given the client exists in the database, " +
            "when making a request for a token using valid credentials, " +
            "then the service generates an access token"
    )
    public void givenClientCredentialsGrantType_whenRequestToken_thenGenerateToken() throws Exception {
        mockMvc.perform(post("/oauth/token")
                        .with(httpBasic("client1", "secret"))
                        .queryParam("grant_type", "client_credentials")
                        .queryParam("scope", "info")
                )
                .andDo(print())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given the client and user exist in the database, " +
            "when making a request for a token using valid credentials, " +
            "then the service generates an access token"
    )
    public void givenPasswordGrantType_whenRequestToken_thenGenerateToken() throws Exception {
        mockMvc.perform(post("/oauth/token")
                        .with(httpBasic("client", "secret"))
                        .queryParam("grant_type", "password")
                        .queryParam("username", "user")
                        .queryParam("password", "password")
                        .queryParam("scope", "read")
                )
                .andDo(print())
                .andExpect(jsonPath("access_token").exists())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given missing client credentials, " +
            "when making a request for a token using valid user credentials, " +
            "then the service returns Unauthorized status"
    )
    public void givenMissingClientCredentials_whenRequestToken_thenReturnUnauthorized() throws Exception {
        mockMvc.perform(post("/oauth/token")
                        .queryParam("grant_type", "password")
                        .queryParam("username", "user")
                        .queryParam("password", "password")
                        .queryParam("scope", "read")
                )
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(header().string("WWW-Authenticate", "Basic realm=\"oauth2/client\""));
    }

    @Test
    @DisplayName("Given missing user credentials, " +
            "when making a request for a token using valid client credentials, " +
            "then the service returns Bad request status"
    )
    public void givenMissingUserCredentials_whenRequestToken_thenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/oauth/token")
                        .with(httpBasic("client", "secret"))
                        .queryParam("grant_type", "password")
                        .queryParam("scope", "read")
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error").value("invalid_grant"))
                .andExpect(jsonPath("error_description").value("Bad credentials"));
    }

    @Test
    @DisplayName("Given the client and user exist in the database, " +
            "when making a request for a token using valid credentials but unauthorized scope, " +
            "then the service returns Bad request status"
    )
    public void givenClientUnauthorizedScope_whenRequestToken_thenReturnBadRequest() throws Exception {
        mockMvc.perform(post("/oauth/token")
                        .with(httpBasic("client", "secret"))
                        .queryParam("grant_type", "password")
                        .queryParam("username", "user")
                        .queryParam("password", "password")
                        .queryParam("scope", "write")
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("error").value("invalid_scope"))
                .andExpect(jsonPath("error_description").value("Invalid scope: write"))
                .andExpect(jsonPath("scope").value("read"));
    }
}
