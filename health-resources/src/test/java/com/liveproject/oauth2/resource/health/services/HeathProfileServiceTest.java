package com.liveproject.oauth2.resource.health.services;

import com.liveproject.oauth2.resource.health.security.context.TestUser;
import com.liveproject.oauth2.resource.health.entities.HealthProfile;
import com.liveproject.oauth2.resource.health.exceptions.HealthProfileAlreadyExistsException;
import com.liveproject.oauth2.resource.health.exceptions.NonExistentHealthProfileException;
import com.liveproject.oauth2.resource.health.repositories.HealthProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Some tests rely on data loaded from data.sql.
 */
@ActiveProfiles("test")
@SpringBootTest
public class HeathProfileServiceTest {

    @Autowired
    private HealthProfileService healthProfileService;

    @Autowired
    private HealthProfileRepository healthProfileRepository;

    @Test
    @TestUser(username = "paolo")
    @DisplayName("given a new profile, assert that the profile is added to the database")
    public void givenNewProfile_assertProfileIsAddedToTheDatabase() {
        HealthProfile profile = new HealthProfile();
        profile.setUsername("paolo");

        healthProfileService.addHealthProfile(profile);
    }

    @Test
    @TestUser(username = "paul")
    @DisplayName("given an existing profile, assert that the profile is not added to the database")
    public void givenExistingProfile_assertProfileIsNotAddedToTheDatabase() {
        HealthProfile profile = new HealthProfile();
        profile.setUsername("paul");    // this profile already exists

        var error = assertThrows(HealthProfileAlreadyExistsException.class, () -> {
            healthProfileService.addHealthProfile(profile);
        });

        assertThat(error.getMessage()).isEqualTo("This health profile already exists.");
    }

    @Test
    @TestUser(username = "paul")
    @DisplayName("given an existing profile, assert that the profile is returned")
    public void givenExistingProfile_whenFindProfile_assertProfileIsReturned() {
        HealthProfile profile = healthProfileService.findHealthProfile("paul");

        assertThat(profile).isNotNull();
    }

    @Test
    @TestUser(username = "paul", authorities = "admin")
    @DisplayName("given a non existing profile, assert error")
    public void givenNonExistingProfile_whenFindProfile_assertError() {
        var error = assertThrows(NonExistentHealthProfileException.class, () -> {
            healthProfileService.findHealthProfile("joe");
        });

        assertThat(error.getMessage()).isEqualTo("No profile found for the provided username.");
    }

    @Test
    @TestUser(username = "paul", authorities = "admin")
    @DisplayName("given an existing profile, assert that the profile is deleted")
    public void givenExistingProfile_whenDeleteProfile_assertProfileIsDeleted() {
        healthProfileService.deleteHealthProfile("yvette");

        assertThat(healthProfileRepository.findHealthProfileByUsername("yvette")).isEmpty();

    }

    @Test
    @TestUser(username = "paul", authorities = "admin")
    @DisplayName("given a non existing profile, assert then when deleting profile an error is returned")
    public void givenNonExistingProfile_whenDeleteProfile_assertError() {
        var error = assertThrows(NonExistentHealthProfileException.class, () -> {
            healthProfileService.deleteHealthProfile("venkat");
        });

        assertThat(error.getMessage()).isEqualTo("No profile found for the provided username.");
    }
}
