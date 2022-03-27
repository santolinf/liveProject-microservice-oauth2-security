package com.liveproject.oauth2.resource.health.repositories;

import com.liveproject.oauth2.resource.health.entities.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Integer> {

  Optional<HealthProfile> findHealthProfileByUsername(String username);
}
