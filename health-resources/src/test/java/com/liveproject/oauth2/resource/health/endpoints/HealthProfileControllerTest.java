package com.liveproject.oauth2.resource.health.endpoints;

import com.liveproject.oauth2.resource.health.BaseTests;
import com.liveproject.oauth2.resource.health.entities.HealthProfile;
import com.liveproject.oauth2.resource.health.services.HealthProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HealthProfileControllerTest extends BaseTests {

    @MockBean
    private HealthProfileService healthProfileService;

    @Test
    @DisplayName("given an authenticated user request, assert that profiles are created and that the service is called")
    public void givenAuthenticatedRequest_whenAddProfile_thenCallService() throws Exception {
        HealthProfile profile = new HealthProfile();
        profile.setUsername("jim");

        mockMvc.perform(post("/profile")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andDo(print()).andExpect(status().isOk());

        verify(healthProfileService).addHealthProfile(profile);
    }

    @Test
    @DisplayName("given an unauthenticated user request, assert that profile is not created and that the service is never called")
    public void givenUnauthenticatedRequest_whenAddProfile_thenReturnUnauthorized() throws Exception {
        HealthProfile profile = new HealthProfile();
        profile.setUsername("anne");

        mockMvc.perform(post("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andDo(print()).andExpect(status().isUnauthorized());

        verify(healthProfileService, never()).addHealthProfile(profile);
    }

    @Test
    @DisplayName("given an authenticated user request, assert that profile can only be deleted by admins")
    public void givenAuthenticatedRequest_whenDeleteProfile_assertOnlyAdminCanDoIt() throws Exception {
        HealthProfile profile = new HealthProfile();
        profile.setUsername("mary");

        mockMvc.perform(post("/profile")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profile)))
                .andDo(print()).andExpect(status().isOk());

        verify(healthProfileService).addHealthProfile(profile);

        // try to delete profile without correct authority
        mockMvc.perform(delete("/profile/mary")
                        .with(jwt()))
                .andDo(print()).andExpect(status().isForbidden());

        // now delete profile as admin
        mockMvc.perform(delete("/profile/mary")
                        .with(jwt().authorities(new SimpleGrantedAuthority("admin"))))
                .andDo(print()).andExpect(status().isOk());

        verify(healthProfileService).deleteHealthProfile("mary");
    }
}
