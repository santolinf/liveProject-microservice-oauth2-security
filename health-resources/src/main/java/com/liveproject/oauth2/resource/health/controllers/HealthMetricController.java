package com.liveproject.oauth2.resource.health.controllers;

import com.liveproject.oauth2.resource.health.entities.HealthMetric;
import com.liveproject.oauth2.resource.health.services.HealthMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metric")
@RequiredArgsConstructor
public class HealthMetricController {

  private final HealthMetricService healthMetricService;

  @PostMapping
  public void addHealthMetric(@RequestBody HealthMetric healthMetric) {
    healthMetricService.addHealthMetric(healthMetric);
  }

  @GetMapping("")
  public List<HealthMetric> findHealthMetrics() {
    return healthMetricService.findHealthMetricHistory();
  }

  @DeleteMapping("/{username}")
  public void deleteHealthMetricForUser(@PathVariable String username) {
    healthMetricService.deleteHealthMetricForUser(username);
  }
}
