package com.liveproject.oauth2.authorization.server.repository;

import com.liveproject.oauth2.authorization.server.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByClientId(String clientId);
}
