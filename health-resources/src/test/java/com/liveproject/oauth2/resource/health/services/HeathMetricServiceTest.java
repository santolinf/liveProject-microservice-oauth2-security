package com.liveproject.oauth2.resource.health.services;

import com.liveproject.oauth2.resource.health.entities.HealthMetric;
import com.liveproject.oauth2.resource.health.entities.HealthProfile;
import com.liveproject.oauth2.resource.health.entities.enums.HealthMetricType;
import com.liveproject.oauth2.resource.health.exceptions.NonExistentHealthProfileException;
import com.liveproject.oauth2.resource.health.repositories.HealthMetricRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Some tests rely on data loaded from data.sql.
 */
@ActiveProfiles("test")
@SpringBootTest
public class HeathMetricServiceTest {

    @Autowired
    private HealthMetricService healthMetricService;

    @Autowired
    private HealthMetricRepository healthMetricRepository;

    private HealthMetric createDefaultMetricFor(String username) {
        HealthMetric metric = new HealthMetric();
        HealthProfile profile = new HealthProfile();
        profile.setUsername(username);
        metric.setProfile(profile);
        metric.setType(HealthMetricType.ECG);
        metric.setValue(60);

        return metric;
    }

    @Test
    @DisplayName("given an existing profile, assert that metrics are added to the database")
    public void givenExistingProfile_assertMetricIsAddedToTheDatabase() {
        HealthMetric metric = createDefaultMetricFor("paul");
        healthMetricService.addHealthMetric(metric);
    }

    @Test
    @DisplayName("given an existing metric, assert that the metric is not added to the database")
    public void givenExistingMetric_assertMetricIsNotAddedToTheDatabase() {
        HealthMetric metric = createDefaultMetricFor("jane");

        var error = assertThrows(NonExistentHealthProfileException.class, () -> {
            healthMetricService.addHealthMetric(metric);
        });

        assertThat(error.getMessage()).isEqualTo("The profile doesn't exist");
    }

    @Test
    @DisplayName("given existing metric history, assert that the metrics are returned")
    public void givenExistingMetricHistory_whenFindMetrics_assertHistoryIsReturned() {
        List<HealthMetric> metrics = healthMetricService.findHealthMetricHistory("paul");

        assertThat(metrics).isNotEmpty();
    }

    @Test
    @DisplayName("given an existing profile, assert that the metrics are deleted")
    public void givenExistingProfile_whenDeleteMetric_assertMetricsAreDeleted() {
        healthMetricService.deleteHealthMetricForUser("yvette");

        assertThat(healthMetricRepository.findHealthMetricHistory("yvette")).isEmpty();

    }

    @Test
    @DisplayName("given a non existing profile, assert then when deleting metrics an error is returned")
    public void givenNonExistingProfile_whenDeleteMetric_assertError() {
        var error = assertThrows(NonExistentHealthProfileException.class, () -> {
            healthMetricService.deleteHealthMetricForUser("venkat");
        });

        assertThat(error.getMessage()).isEqualTo("The profile doesn't exist");
    }
}
