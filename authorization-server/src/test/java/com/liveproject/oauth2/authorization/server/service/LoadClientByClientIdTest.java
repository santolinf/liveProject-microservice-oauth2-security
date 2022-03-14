package com.liveproject.oauth2.authorization.server.service;

import com.liveproject.oauth2.authorization.server.BaseTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoadClientByClientIdTest extends BaseTests {

    @Autowired
    private ApplicationClientDetailsService clientDetailsService;

    @Test
    @DisplayName("given that the client does not exist in the database, assert that the method throws ClientRegistrationException")
    public void givenUnknownClient_whenCallingLoadClientByClientId_thenReturnError() {
        var clientId = "client2";

        var error = assertThrows(ClientRegistrationException.class, () -> {
            clientDetailsService.loadClientByClientId(clientId);
        });

        assertThat(error.getMessage()).isEqualTo(clientId);
    }

    @Test
    @DisplayName("given that the client exists in the database, assert that the client details are loaded from database")
    public void givenKnownUser_whenCallingLoadUserByUsername_thenReturnUserDetails() {
        // we know that the "client1" details are part of the initial test data loaded by Spring Boot Test
        var clientId = "client1";

        var clientDetails = clientDetailsService.loadClientByClientId(clientId);

        assertThat(clientDetails).isNotNull();
    }
}
