package com.liveproject.oauth2.resource.health.endpoints;

import com.liveproject.oauth2.resource.health.BaseTests;
import com.liveproject.oauth2.resource.health.controllers.dto.HealthAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HealthAdviceControllerTest extends BaseTests {

    @Test
    @DisplayName("given an authenticated user request, assert that advice can not be posted by anyone")
    public void givenAuthenticatedRequest_whenAddAdviceWithoutAuthority_thenReturnForbidden() throws Exception {
        HealthAdvice advice = new HealthAdvice();
        advice.setUsername("mary");
        advice.setAdvice("advice text");

        mockMvc.perform(post("/advice")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(advice))))
                .andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("given an authenticated user request, assert that with the correct authority an advice can be posted")
    public void givenAuthenticatedRequest_whenAddAdvice_thenIsSuccessfullyProcessed() throws Exception {
        HealthAdvice advice = new HealthAdvice();
        advice.setUsername("john");
        advice.setAdvice("advice text");

        mockMvc.perform(post("/advice")
                        .with(jwt().authorities(new SimpleGrantedAuthority("advice")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(advice))))
                .andDo(print()).andExpect(status().isOk());
    }
}
