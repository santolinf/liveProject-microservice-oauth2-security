package com.liveproject.oauth2.authorization.server.repository;

import com.liveproject.oauth2.authorization.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);
}
