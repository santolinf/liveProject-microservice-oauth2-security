package com.liveproject.oauth2.resource.health.endpoints;

import com.liveproject.oauth2.resource.health.BaseTests;
import com.liveproject.oauth2.resource.health.entities.HealthMetric;
import com.liveproject.oauth2.resource.health.entities.HealthProfile;
import com.liveproject.oauth2.resource.health.entities.enums.HealthMetricType;
import com.liveproject.oauth2.resource.health.services.HealthMetricService;
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

public class HealthMetricControllerTest extends BaseTests {

    @MockBean
    private HealthMetricService healthMetricService;

    @Test
    @DisplayName("given an authenticated user request, assert that metrics are created and that the service is called")
    public void givenAuthenticatedRequest_whenAddMetric_thenCallService() throws Exception {
        HealthMetric metric = new HealthMetric();
        metric.setType(HealthMetricType.ECG);
        metric.setValue(80);

        mockMvc.perform(post("/metric")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metric)))
                .andDo(print()).andExpect(status().isOk());

        verify(healthMetricService).addHealthMetric(metric);
    }

    @Test
    @DisplayName("given an unauthenticated user request, assert that metrics are not created and that the service is never called")
    public void givenUnauthenticatedRequest_whenAddMetric_thenReturnUnauthorized() throws Exception {
        HealthMetric metric = new HealthMetric();
        metric.setType(HealthMetricType.BLOOD_OXYGEN_LEVEL);
        metric.setValue(99);

        mockMvc.perform(post("/metric")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metric)))
                .andDo(print()).andExpect(status().isUnauthorized());

        verify(healthMetricService, never()).addHealthMetric(metric);
    }

    @Test
    @DisplayName("given an authenticated user request, assert that metrics can only be deleted by admins")
    public void givenAuthenticatedRequest_whenDeleteMetric_assertOnlyAdminCanDoIt() throws Exception {
        HealthMetric metric = new HealthMetric();
        HealthProfile profile = new HealthProfile();
        profile.setUsername("mary");
        metric.setProfile(profile);
        metric.setType(HealthMetricType.HEART_RATE);
        metric.setValue(60);

        mockMvc.perform(post("/metric")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metric)))
                .andDo(print()).andExpect(status().isOk());

        verify(healthMetricService).addHealthMetric(metric);

        // try to delete metric without correct authority
        mockMvc.perform(delete("/metric/mary")
                        .with(jwt()))
                .andDo(print()).andExpect(status().isForbidden());

        // now delete profile as admin
        mockMvc.perform(delete("/metric/mary")
                        .with(jwt().authorities(new SimpleGrantedAuthority("admin"))))
                .andDo(print()).andExpect(status().isOk());


        verify(healthMetricService).deleteHealthMetricForUser("mary");
    }
}
