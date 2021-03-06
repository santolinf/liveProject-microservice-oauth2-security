package com.liveproject.oauth2.resource.health.services;

import com.liveproject.oauth2.resource.health.entities.HealthProfile;
import com.liveproject.oauth2.resource.health.exceptions.HealthProfileAlreadyExistsException;
import com.liveproject.oauth2.resource.health.exceptions.NonExistentHealthProfileException;
import com.liveproject.oauth2.resource.health.repositories.HealthProfileRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class HealthProfileService {

  private final HealthProfileRepository healthProfileRepository;

  public HealthProfileService(HealthProfileRepository healthProfileRepository) {
    this.healthProfileRepository = healthProfileRepository;
  }

  @PreAuthorize("#profile.username == authentication.principal.claims['user_name']")
  public void addHealthProfile(HealthProfile profile) {
    Optional<HealthProfile> healthProfile = healthProfileRepository.findHealthProfileByUsername(profile.getUsername());

    if (healthProfile.isEmpty()) {
      healthProfileRepository.save(profile);
    } else {
      throw new HealthProfileAlreadyExistsException("This health profile already exists.");
    }
  }

  @PreAuthorize("#username == authentication.principal.claims['user_name'] or hasAuthority('admin')")
  public HealthProfile findHealthProfile(String username) {
    Optional<HealthProfile> healthProfile =
            healthProfileRepository.findHealthProfileByUsername(username);

    return healthProfile
            .orElseThrow(() -> new NonExistentHealthProfileException("No profile found for the provided username."));
  }

  @PreAuthorize("hasAuthority('admin')")
  public void deleteHealthProfile(String username) {
    Optional<HealthProfile> healthProfile =
            healthProfileRepository.findHealthProfileByUsername(username);

    healthProfile.ifPresentOrElse(
            p -> healthProfileRepository.delete(p),
            () -> {
              throw new NonExistentHealthProfileException("No profile found for the provided username.");
            });
  }
}
