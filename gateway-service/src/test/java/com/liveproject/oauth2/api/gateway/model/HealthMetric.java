package com.liveproject.oauth2.api.gateway.model;

import com.liveproject.oauth2.api.gateway.model.enums.HealthMetricType;

public class HealthMetric {

  private double value;

  private HealthMetricType type;

  private HealthProfile profile;

  public HealthProfile getProfile() {
    return profile;
  }

  public void setProfile(HealthProfile profile) {
    this.profile = profile;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  public HealthMetricType getType() {
    return type;
  }

  public void setType(HealthMetricType type) {
    this.type = type;
  }
}
