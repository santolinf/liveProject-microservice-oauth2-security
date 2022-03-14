package com.liveproject.oauth2.authorization.server.service;

import com.liveproject.oauth2.authorization.server.BaseTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoadUserByUsernameTest extends BaseTests {

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Test
    @DisplayName("given that the user does not exist in the database, assert that the method throws UsernameNotFoundException")
    public void givenUnknownUser_whenCallingLoadUserByUsername_thenReturnError() {
        var username = "user1";

        var error = assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(username);
        });

        assertThat(error.getMessage()).isEqualTo(username);
    }

    @Test
    @DisplayName("given that the user exists in the database, assert that the user details are loaded from database")
    public void givenKnownUser_whenCallingLoadUserByUsername_thenReturnUserDetails() {
        // we know that the "user" details are part of the initial test data loaded by Spring Boot Test
        var username = "user";

        var userDetails = userDetailsService.loadUserByUsername(username);

        assertThat(userDetails).isNotNull();
    }
}
