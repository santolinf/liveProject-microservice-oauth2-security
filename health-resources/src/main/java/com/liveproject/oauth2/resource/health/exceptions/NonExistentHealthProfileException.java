package com.liveproject.oauth2.resource.health.exceptions;

public class NonExistentHealthProfileException extends RuntimeException {

  public NonExistentHealthProfileException(String message) {
    super(message);
  }
}
