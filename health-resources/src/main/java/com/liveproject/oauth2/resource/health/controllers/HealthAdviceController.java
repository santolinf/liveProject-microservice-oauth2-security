package com.liveproject.oauth2.resource.health.controllers;

import com.liveproject.oauth2.resource.health.controllers.dto.HealthAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/advice")
public class HealthAdviceController {

  @PostMapping
  public void provideHealthAdviceCallback(@RequestBody List<HealthAdvice> healthAdvice) {
    healthAdvice.forEach(h -> log.info("Advice for: " + h.getUsername() + " Advice text: "+h.getAdvice()));
  }
}
