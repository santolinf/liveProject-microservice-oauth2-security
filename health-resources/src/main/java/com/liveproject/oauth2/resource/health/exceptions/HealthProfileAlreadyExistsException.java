package com.liveproject.oauth2.resource.health.exceptions;

public class HealthProfileAlreadyExistsException extends RuntimeException {

  public HealthProfileAlreadyExistsException(String message) {
    super(message);
  }
}
