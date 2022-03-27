package com.liveproject.oauth2.resource.health.entities;

import com.liveproject.oauth2.resource.health.entities.enums.HealthMetricType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "health_metric")
public class HealthMetric {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Basic
  private double value;

  @Enumerated(EnumType.STRING)
  private HealthMetricType type;

  @ManyToOne
  private HealthProfile profile;
}
