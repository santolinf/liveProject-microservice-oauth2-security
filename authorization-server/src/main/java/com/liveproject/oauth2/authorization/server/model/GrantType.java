package com.liveproject.oauth2.authorization.server.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "GRANT_TYPE")
public class GrantType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String grantType;

    @ManyToOne
    private Client client;
}
