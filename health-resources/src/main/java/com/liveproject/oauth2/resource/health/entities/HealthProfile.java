package com.liveproject.oauth2.resource.health.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "health_profile")
public class HealthProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String username;

  @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE)
  @JsonIgnore
  private List<HealthMetric> metrics;
}
