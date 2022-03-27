package com.liveproject.oauth2.resource.health.repositories;

import com.liveproject.oauth2.resource.health.entities.HealthMetric;
import com.liveproject.oauth2.resource.health.entities.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HealthMetricRepository extends JpaRepository<HealthMetric, Integer> {

  @Query("SELECT h FROM HealthMetric h WHERE h.profile.username=:username")
  List<HealthMetric> findHealthMetricHistory(@Param("username") String username);

  @Query("DELETE FROM HealthMetric h WHERE h.profile=:profile")
  @Modifying
  void deleteAllForUser(@Param("profile") HealthProfile profile);
}
