package com.liveproject.oauth2.authorization.server.service;

import com.liveproject.oauth2.authorization.server.exeption.UserAlreadyExistsException;
import com.liveproject.oauth2.authorization.server.model.User;
import com.liveproject.oauth2.authorization.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Add a new user to the database
     */
    public void addUser(User user) {
        // check that the username does not already exist
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }

        if (Objects.nonNull(user.getAuthorities())) {
            user.getAuthorities().forEach(authority -> authority.setUser(user));
        }
        userRepository.save(user);
    }

    /**
     * Fetch all the user details from the database
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
