package com.liveproject.oauth2.resource.health.controllers;

// TODO import org.springframework.security.core.Authentication;
import com.liveproject.oauth2.resource.health.entities.HealthProfile;
import com.liveproject.oauth2.resource.health.services.HealthProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class HealthProfileController {

  private final HealthProfileService healthProfileService;

  @PostMapping
  public void addHealthProfile(@RequestBody HealthProfile healthProfile) {
    healthProfileService.addHealthProfile(healthProfile);
  }

  @GetMapping("/{username}")
  public HealthProfile findHealthProfile(@PathVariable String username) {
    return healthProfileService.findHealthProfile(username);
  }

  @DeleteMapping("/{username}")
  // TODO re-enable after adding security
  // public void deleteHealthProfile(@PathVariable String username, Authentication a) {
  public void deleteHealthProfile(@PathVariable String username ) {
    healthProfileService.deleteHealthProfile(username);
  }
}
